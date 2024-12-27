package com.ISA.OnlyBunsBackend.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "last_login")
public class LastLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private User user;

    @Column(name = "last_login_time", nullable = false)
    private LocalDateTime lastLoginTime;

    @Column(name = "total_followers", nullable = false)
    private Integer totalFollowers;

    @Column(name = "total_likes", nullable = false)
    private Integer totalLikes;

    @Column(name = "total_comments", nullable = false)
    private Integer totalComments;

    public LastLogin() {}

    public LastLogin(Integer id, User user, LocalDateTime lastLoginTime, Integer totalFollowers, Integer totalLikes, Integer totalComments) {
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

    @Override
    public String toString() {
        return "LastLogin[" +
                "id=" + id +
                ", user=" + user +
                ", lastLoginTime=" + lastLoginTime +
                ", totalFollowers=" + totalFollowers +
                ", totalLikes=" + totalLikes +
                ", totalComments=" + totalComments +
                ']';
    }



}
