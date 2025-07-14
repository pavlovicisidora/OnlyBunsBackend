package com.ISA.OnlyBunsBackend.service;


import com.ISA.OnlyBunsBackend.dto.ChatDTO;
import com.ISA.OnlyBunsBackend.dto.MessageDTO;

import java.util.List;

public interface ChatService {
    List<ChatDTO> getUserChats(String username);
    List<MessageDTO> getChatMessages(Integer chatId);
    ChatDTO createPrivateChat(String username1, String username2);
    ChatDTO createGroupChat(String name, String adminUsername, List<String> membersUsernames);
    ChatDTO addParticipantToGroup(Integer chatId, String username, String loggedInUser);
    void removeParticipantFromGroup(Integer chatId, String username, String loggedInUser);
    MessageDTO sendMessage(Integer chatId, String senderUsername, String content);
}
