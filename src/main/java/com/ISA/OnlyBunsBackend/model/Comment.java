package com.ISA.OnlyBunsBackend.model;

public class Comment {
    private int id;
    private User user;
    private String text;

    public Comment() {
    }

    public Comment(int id, User user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}