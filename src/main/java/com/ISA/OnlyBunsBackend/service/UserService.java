package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {
    Integer getFollowersCount(Integer userId);
    public List<UsersViewDTO> getAllUsers();
    public List<UsersViewDTO> searchUsers(String firstName, String lastName, String email, String minPosts, String maxPosts, String sortBy, String sortDirection);
}
