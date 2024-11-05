package com.ISA.OnlyBunsBackend.model;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private String description;
    private String image;
    private String location;
    private LocalDateTime timeOfPublishing;

    public Post() {}
    public Post(int id, String description, String image, String location, LocalDateTime timeOfPublishing) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.location = location;
        this.timeOfPublishing = timeOfPublishing;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimeOfPublishing() {
        return timeOfPublishing;
    }

    public void setTimeOfPublishing(LocalDateTime timeOfPublishing) {
        this.timeOfPublishing = timeOfPublishing;
    }
}
