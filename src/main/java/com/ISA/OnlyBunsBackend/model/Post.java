package com.ISA.OnlyBunsBackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "timeOfPublishing", nullable = false)
    private LocalDateTime timeOfPublishing;


    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_user_likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> userLikes = new HashSet<>();

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted = false;

    public Post() {}
    public Post(Integer id, User user, String description, String image, Location location, LocalDateTime timeOfPublishing, Set<Comment> comments, Set<User> userLikes, boolean isDeleted) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.image = image;
        this.location = location;
        this.timeOfPublishing = timeOfPublishing;
        this.comments = comments;
        this.userLikes = userLikes;
        this.isDeleted = isDeleted;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<User> userLikes) {
        this.userLikes = userLikes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getLikesCount() {
        return userLikes.size();
    }

    @Override
    public int hashCode() {
        return 1337;
    }

    @Override
    public String toString() {
        return "Post [id=" + id +
                ", description=" + description +
                ", image=" + image +
                ", location=" + (location != null ? location.toString() : "null") +
                ", timeOfPublishing=" + timeOfPublishing +
                ", comments=" + (comments != null ? comments.size() : "null") +
                ", userLikes=" + (userLikes != null ? userLikes.size() : "null") +
                ", isDeleted=" + isDeleted +
                "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Post c = (Post) obj;
        return id != null && id.equals(c.getId());
        }
}