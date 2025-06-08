package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Message;

import java.time.LocalDateTime;

public class MessageDTO {
    private Integer id;
    private UsersViewDTO sender;
    private String content;
    private LocalDateTime timestamp;
    private Integer chatId;
    public MessageDTO() {}
    public MessageDTO(Message message){
        this.id = message.getId();
        this.sender = new UsersViewDTO(message.getSender());
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
        this.chatId = message.getChat().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UsersViewDTO getSender() {
        return sender;
    }

    public void setSender(UsersViewDTO sender) {
        this.sender = sender;
    }
    public Integer getChatId() {
        return chatId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
}
