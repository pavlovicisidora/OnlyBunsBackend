package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;

public class CommentDTO {
    private int id;
    private UserRegistration user;
    private String text;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        id = comment.getId();
        user = new UserRegistration(comment.getUser());
        text = comment.getText();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRegistration getUser() {
        return user;
    }

    public void setUser(UserRegistration user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
