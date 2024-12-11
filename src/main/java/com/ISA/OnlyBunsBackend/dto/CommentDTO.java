package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer id;
    private int userId;
    private String text;
    private String authorName;
    private LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.text = comment.getText();
        this.authorName = comment.getUser().getFullName();
        this.createdAt = comment.getCreatedAt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
}
