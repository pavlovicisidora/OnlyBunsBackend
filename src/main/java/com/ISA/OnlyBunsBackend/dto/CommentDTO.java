package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;

public class CommentDTO {
    private Integer id;
    private String text;
    private String authorName;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.authorName = comment.getUser().getFullName();
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
}
