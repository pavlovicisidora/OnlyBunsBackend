package com.ISA.OnlyBunsBackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location")
    private Location location;

    @Column(name = "timeOfPublishing", nullable = false)
    private LocalDateTime timeOfPublishing;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> userLikes;

    public Post() {}
    public Post(Integer id, String description, String image, Location location, LocalDateTime timeOfPublishing, List<Comment> comments, List<User> userLikes) {
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
