package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
    User save(UserRegistration userRequest);
    User updateUser(User updatedUser);

}
