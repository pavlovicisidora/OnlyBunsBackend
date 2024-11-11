package com.ISA.OnlyBunsBackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(int id, User user, Post post, String text, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.text = text;
        this.createdAt = createdAt;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", post=" + (post != null ? post.getId() : "null") +
                ", text=" + text +
                ", created_at=" + createdAt + "]";
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
