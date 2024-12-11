package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public class PostViewDTO {
    private Integer id;
    private int userId;
    private String description;
    private String image;
    private int likeCount;
    private List<CommentDTO> comments;
    private boolean isDeleted = false;

    /*Iva*/
    private LocalDateTime timeOfPublishing;

    public PostViewDTO() {
    }

    public PostViewDTO(Integer id, int userId, String description, String image, int likeCount,
                       List<CommentDTO> comments, boolean isDeleted, LocalDateTime timeOfPublishing) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.image = image;
        this.likeCount = likeCount;
        this.comments = comments;
        this.isDeleted = isDeleted;
        this.timeOfPublishing = timeOfPublishing;
    }

    public PostViewDTO(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.description = post.getDescription();
        this.image = post.getImage();
        this.likeCount = post.getLikesCount();
        this.comments = post.getComments().stream().map(CommentDTO::new).toList();
        this.isDeleted = post.isDeleted();
        this.timeOfPublishing = post.getTimeOfPublishing();
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
    public LocalDateTime getTimeOfPublishing() {return timeOfPublishing;}
    public void setTimeOfPublishing(LocalDateTime timeOfPublishing) { this.timeOfPublishing = timeOfPublishing;}

    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId = userId;}
}
