package com.ISA.OnlyBunsBackend.service;


import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.User;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserService {
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll ();
    User save(UserRegistration userRequest);
    User updateUser(User updatedUser);

    Integer getFollowersCount(Integer userId);
    List<UsersViewDTO> getAllUsers();
    List<UsersViewDTO> searchUsers(String firstName, String lastName, String email, String minPosts, String maxPosts, String sortBy, String sortDirection);

}
