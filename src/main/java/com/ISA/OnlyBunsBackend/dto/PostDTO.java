package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class PostDTO {
    private Integer id;
    private User user;
    private String description;
    private String image;
    private Location location;
    private LocalDateTime timeOfPublishing;
    private boolean isDeleted = false;

    public PostDTO() {}

    public PostDTO(LocalDateTime timeOfPublishing, Location location, String image, String description, User user, Integer id){
        this.timeOfPublishing = timeOfPublishing;
        this.location = location;
        this.image = image;
        this.description = description;
        this.user = user;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
