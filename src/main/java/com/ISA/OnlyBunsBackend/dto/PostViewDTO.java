package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Post;

import java.util.List;

public class PostViewDTO {
    private Integer id;
    private String description;
    private String image;
    private int likeCount;
    private List<CommentDTO> comments;
    private boolean isDeleted = false;

    public PostViewDTO() {
    }

    public PostViewDTO(Integer id, String description, String image, int likeCount, List<CommentDTO> comments, boolean isDeleted) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.likeCount = likeCount;
        this.comments = comments;
        this.isDeleted = isDeleted;
    }

    public PostViewDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.image = post.getImage();
        this.likeCount = post.getLikesCount();
        this.comments = post.getComments().stream().map(CommentDTO::new).toList();
        this.isDeleted = post.isDeleted();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
