package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.UserDTO;
import java.util.List;

public interface UserService {
    Integer getFollowersCount(Integer userId);
    public List<UserDTO> getAllUsers();
    public List<UserDTO> searchUsers(String firstName, String lastName, String email, Integer minPosts, Integer maxPosts);
}
