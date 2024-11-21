package com.ISA.OnlyBunsBackend.service;


import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.User;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;


import java.util.List;

public interface UserService {
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll ();
    User save(UserRegistration userRequest);
    User updateUser(User updatedUser);

    Integer getFollowersCount(Integer userId);
    Page<UsersViewDTO> getAllUsers(Pageable pageable);
    Page<UsersViewDTO> searchUsers(Pageable pageable, String firstName, String lastName, String email, String minPosts, String maxPosts, String sortBy, String sortDirection);

    UsersViewDTO getUser(Integer id);
    void deleteInactiveUsers();

    @Scheduled(cron = "0 0 0 28-31 * ?")
    void scheduleInactiveUserDeletion();

    void followUser(Integer followerId, Integer followedId);
    void unfollowUser(Integer followerId, Integer followedId);
    boolean isFollowing(int followerId, int followedUserId);
}
