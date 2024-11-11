package com.ISA.OnlyBunsBackend.dto;

import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.Role;
import com.ISA.OnlyBunsBackend.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRegistration {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocationDTO location;
    private boolean isActivated = false;

    public UserRegistration() {
    }

    public UserRegistration(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.location = new LocationDTO(user.getLocation());
        this.isActivated = user.isActivated();

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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
