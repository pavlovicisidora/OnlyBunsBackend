package com.ISA.OnlyBunsBackend.model;

import com.ISA.OnlyBunsBackend.enums.UserType;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "isActivated", nullable = false)
    private boolean isActivated = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_relations",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id")
    )
    private Set<User> followings = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_relations",
            joinColumns = @JoinColumn(name = "followed_user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    public User(){}
    public User(String username, String password, String firstName, String lastName, String email, UserType type, Location location, boolean isActivated, Set<User> followers, Set<User> followings, Set<Post> posts)
    {
        super();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.location = location;
        this.isActivated = isActivated;
        this.followers = followers;
        this.followings = followings;
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<User> followings) {
        this.followings = followings;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User [id=" + id +
                ", username=" + username +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", type=" + type +
                ", location=" + (location != null ? location.toString() : "null") +
                ", isActivated=" + isActivated +
                ", followers=" + (followers != null ? followers.size() : "null") +
                ", followings=" + (followings != null ? followings.size() : "null") +
                ", posts=" + (posts != null ? posts.size() : "null") +
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User u = (User) o;
        if (u.username == null || username == null) {
            return false;
        }
        return Objects.equals(username, u.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    public int getPostCount() {
        return posts != null ? posts.size() : 0;
    }

    public int getFollowersCount() {
        return followers != null ? followers.size() : 0;
    }
}
