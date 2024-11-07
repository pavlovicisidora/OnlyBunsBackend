package com.ISA.OnlyBunsBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;


public class Post {

    private int id;
    private String description;
    private String image;
    private Location location;
    private LocalDateTime timeOfPublishing;
    private List<Comment> comments;
    private List<User> userLikes;

    public Post() {}
    public Post(int id, String description, String image, Location location, LocalDateTime timeOfPublishing, List<Comment> comments, List<User> userLikes) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.location = location;
        this.timeOfPublishing = timeOfPublishing;
        this.comments = comments;
        this.userLikes = userLikes;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getTimeOfPublishing() {
        return timeOfPublishing;
    }

    public void setTimeOfPublishing(LocalDateTime timeOfPublishing) {
        this.timeOfPublishing = timeOfPublishing;
    }

    public List<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<User> userLikes) {
        this.userLikes = userLikes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
