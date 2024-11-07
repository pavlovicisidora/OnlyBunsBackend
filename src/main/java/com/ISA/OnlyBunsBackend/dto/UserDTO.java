package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.enums.UserType;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserType type;
    private LocationDTO location;
    private boolean isActivated = false;
    private List<UserDTO> followers;
    private List<UserDTO> followings;
    private List<PostDTO> posts;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.type = user.getType();
        this.location = new LocationDTO(user.getLocation());
        this.isActivated = user.isActivated();
        this.followers = new ArrayList<>();
        for (User follower : user.getFollowers()) {
            this.followers.add(new UserDTO(follower));
        }

        this.followings = new ArrayList<>();
        for (User following : user.getFollowings()) {
            this.followings.add(new UserDTO(following));
        }

        this.posts = new ArrayList<>();
        for (Post post : user.getPosts()) {
            this.posts.add(new PostDTO(post));
        }
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }

    public List<UserDTO> getFollowings() {
        return followings;
    }

    public void setFollowings(List<UserDTO> followings) {
        this.followings = followings;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
