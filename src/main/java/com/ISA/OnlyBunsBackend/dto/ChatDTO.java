package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Chat;
import com.ISA.OnlyBunsBackend.model.ChatType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatDTO {
    private Integer id;
    private String name;
    private ChatType type;
    private String admin;
    private Set<String> members = new HashSet<>();
    private List<MessageDTO> messages;
    public ChatDTO() {}
    public ChatDTO(Chat chat){
        id = chat.getId();
        name = chat.getName();
        type = chat.getType();
        admin = chat.getAdmin() != null ? chat.getAdmin().getUsername() : "N/A";
        members = chat.getMembers().stream()
                .map(user -> {
                    String username = user.getUsername();
                    return username;
                }).collect(Collectors.toSet());
        messages = chat.getMessages().stream()
                .map(message -> {
                    MessageDTO messageDTO = new MessageDTO();
                    messageDTO.setId(message.getId());
                    messageDTO.setSender(new UsersViewDTO(message.getSender()));
                    messageDTO.setContent(message.getContent());
                    messageDTO.setTimestamp(message.getTimestamp());
                    return messageDTO;
                }).toList();;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
