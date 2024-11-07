package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {
    private int id;
    private String description;
    private String image;
    private LocationDTO location;
    private LocalDateTime timeOfPublishing;
    private List<CommentDTO> comments;
    private List<UserDTO> userLikes;

    public PostDTO() {
    }
    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.image = post.getImage();
        this.location = new LocationDTO(post.getLocation());
        this.timeOfPublishing = post.getTimeOfPublishing();
        this.comments = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            this.comments.add(new CommentDTO(comment));
        }

        this.userLikes = new ArrayList<>();
        for (User user : post.getUserLikes()) {
            this.userLikes.add(new UserDTO(user));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public LocalDateTime getTimeOfPublishing() {
        return timeOfPublishing;
    }

    public void setTimeOfPublishing(LocalDateTime timeOfPublishing) {
        this.timeOfPublishing = timeOfPublishing;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<UserDTO> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<UserDTO> userLikes) {
        this.userLikes = userLikes;
    }
}
