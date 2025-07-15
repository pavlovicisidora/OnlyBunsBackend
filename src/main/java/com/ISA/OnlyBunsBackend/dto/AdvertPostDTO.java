package com.ISA.OnlyBunsBackend.dto;

import java.time.LocalDateTime;

public class AdvertPostDTO {
    private String description;
    private LocalDateTime timeOfPublishing;
    private String username;

    public AdvertPostDTO() {
    }

    public AdvertPostDTO(String description, LocalDateTime timeOfPublishing, String username) {
        this.description = description;
        this.timeOfPublishing = timeOfPublishing;
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimeOfPublishing() {
        return timeOfPublishing;
    }

    public void setTimeOfPublishing(LocalDateTime timeOfPublishing) {
        this.timeOfPublishing = timeOfPublishing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
