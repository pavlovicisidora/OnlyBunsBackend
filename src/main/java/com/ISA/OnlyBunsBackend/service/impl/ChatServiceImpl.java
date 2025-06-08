package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.ChatDTO;
import com.ISA.OnlyBunsBackend.dto.MessageDTO;
import com.ISA.OnlyBunsBackend.model.Chat;
import com.ISA.OnlyBunsBackend.model.ChatType;
import com.ISA.OnlyBunsBackend.model.Message;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.ChatRepository;
import com.ISA.OnlyBunsBackend.repository.MessageRepository;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ChatDTO> getUserChats(String username) {
        List<Chat> chats = chatRepository.findAllChatsByUser(username);
        return chats.stream().map(ChatDTO::new).toList();
    }

    @Override
    public List<MessageDTO> getChatMessages(Integer chatId) {
        List<Message> messages = messageRepository.findMessagesByChat(chatId);
        return messages.stream().map(MessageDTO::new).toList();
    }

    @Override
    public ChatDTO createPrivateChat(String username1, String username2) {
        User user1 = userRepository.findByUsername(username1);
        User user2 = userRepository.findByUsername(username2);
        Optional<Chat> existing = chatRepository.findPrivateChatBetweenUsers(user1.getId(), user2.getId());
        if (existing.isPresent()) {
            return new ChatDTO(existing.get());
        }

        Chat chat = new Chat();
        chat.setType(ChatType.PRIVATE);

        chat.getMembers().add(user1);
        chat.getMembers().add(user2);

        Chat savedChat = chatRepository.save(chat);
        return new ChatDTO(savedChat);
    }

    @Override
    public ChatDTO createGroupChat(String name, String adminUsername, List<String> membersUsernames) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setType(ChatType.GROUP);
        User admin = userRepository.findByUsername(adminUsername);
        chat.setAdmin(admin);

        List<User> members = new ArrayList<>();
        for (String username : membersUsernames) {
            User member = userRepository.findByUsername(username);
            members.add(member);
        }

        chat.getMembers().addAll(members);
        chat.getMembers().add(admin);
        Chat savedChat = chatRepository.save(chat);
        return new ChatDTO(savedChat);
    }

    @Override
    public ChatDTO addParticipantToGroup(Integer chatId, String username, String loggedInUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        if (chat.getType() != ChatType.GROUP) {
            throw new IllegalStateException("Cannot add participants to a private chat");
        }
        if (!chat.getAdmin().getUsername().equals(loggedInUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the group admin can add participants.");
        }
        User user = userRepository.findByUsername(username);
        chat.getMembers().add(user);
        chatRepository.save(chat);

        List<MessageDTO> lastMessages = chat.getMessages().stream()
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .limit(10)
                .map(MessageDTO::new)
                .toList();

        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/newMember", lastMessages);

        return new ChatDTO(chat);
    }

    @Override
    public void removeParticipantFromGroup(Integer chatId, String username, String loggedInUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        if (!chat.getAdmin().getUsername().equals(loggedInUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the group admin can remove participants.");
        }

        chat.getMembers().removeIf(u -> u.getUsername().equals(username));
        chatRepository.save(chat);
        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/memberRemoved", username);
    }

    @Override
    public MessageDTO sendMessage(Integer chatId, String senderUsername, String content) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        User sender = userRepository.findByUsername(senderUsername);
        if (!chat.getMembers().contains(sender)) {
            throw new AccessDeniedException("User is not a participant of this chat");
        }

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        return new MessageDTO(savedMessage);
    }
}
