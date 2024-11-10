package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.enums.UserType;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersViewDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private int postCount;
    private int followersCount;

    public UsersViewDTO() {
    }

    public UsersViewDTO(Integer id, String firstName, String lastName, String email, int postCount, int followersCount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.postCount = postCount;
        this.followersCount = followersCount;
    }

    public UsersViewDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();

        this.postCount = user.getPostCount();
        this.followersCount = user.getFollowersCount();
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

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
