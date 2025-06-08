package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.dto.ChatDTO;
import com.ISA.OnlyBunsBackend.dto.MessageDTO;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.service.ChatService;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/chats")
@PreAuthorize("hasRole('USER')")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public List<ChatDTO> getUserChats(@PathVariable String username) {
        return chatService.getUserChats(username);
    }

    @GetMapping("/{chatId}/messages")
    public List<MessageDTO> getChatMessages(@PathVariable Integer chatId) {
        return chatService.getChatMessages(chatId);
    }

    @PostMapping("/private")
    public ChatDTO createPrivateChat(@RequestParam String username1, @RequestParam String username2) {
        return chatService.createPrivateChat(username1, username2);
    }

    @PostMapping("/group")
    public ChatDTO createGroupChat(@RequestParam String name, @RequestParam String adminUsername, @RequestBody List<String> membersUsernames) {
        return chatService.createGroupChat(name, adminUsername, membersUsernames);
    }

    @PostMapping("/{chatId}/add")
    public void addParticipant(@PathVariable Integer chatId, @RequestParam String username, Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        chatService.addParticipantToGroup(chatId, username, loggedInUser.getUsername());
    }

    @PostMapping("/{chatId}/remove")
    public void removeParticipant(@PathVariable Integer chatId, @RequestParam String username, Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        chatService.removeParticipantFromGroup(chatId, username, loggedInUser.getUsername());
    }

    @PostMapping("/{chatId}/message")
    public MessageDTO sendMessage(@PathVariable Integer chatId, @RequestParam String senderUsername, @RequestParam String content) {
        return chatService.sendMessage(chatId, senderUsername, content);
    }
}
