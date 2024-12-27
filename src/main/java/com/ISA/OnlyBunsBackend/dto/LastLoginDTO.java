package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.User;

import java.time.LocalDateTime;

public class LastLoginDTO {
    private Integer id;
    private User user;
    private LocalDateTime lastLoginTime;
    private Integer totalFollowers;
    private Integer totalLikes;
    private Integer totalComments;

    public LastLoginDTO() {
    }

    public LastLoginDTO(Integer id, User user, LocalDateTime lastLoginTime, Integer totalFollowers, Integer totalLikes, Integer totalComments) {
        this.id = id;
        this.user = user;
        this.lastLoginTime = lastLoginTime;
        this.totalFollowers = totalFollowers;
        this.totalLikes = totalLikes;
        this.totalComments = totalComments;
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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(Integer totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }
}