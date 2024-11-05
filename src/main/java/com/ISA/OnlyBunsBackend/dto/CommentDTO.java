package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.User;

public class CommentDTO {
    private int id;
    private UserDTO user;
    private String text;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        id = comment.getId();
        user = new UserDTO(comment.getUser());
        text = comment.getText();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
