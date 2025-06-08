package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.dto.MessageDTO;
import com.ISA.OnlyBunsBackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class WebSocketChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public MessageDTO sendMessage(MessageDTO messageDTO, Principal principal) {
        System.out.println("Primljena poruka: " + messageDTO.getContent());

        String senderUsername = principal.getName();
        MessageDTO savedMessage = chatService.sendMessage(
                messageDTO.getChatId(),
                senderUsername,
                messageDTO.getContent()
        );

        messagingTemplate.convertAndSend("/topic/chat/" + savedMessage.getChatId(), savedMessage);
        System.out.println("Šaljem poruku ka /topic/chat/" + savedMessage.getChatId());

        return savedMessage;
    }
}
