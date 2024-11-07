package com.ISA.OnlyBunsBackend.model;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private User user;

    @Column(name = "text", nullable = false)
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

    @Override
    public String toString() {
        return "Comment [id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", text=" + text +
                "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Comment comment = (Comment) obj;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
