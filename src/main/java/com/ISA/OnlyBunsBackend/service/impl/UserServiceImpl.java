package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer getFollowersCount(Integer userId) {
        return userRepository.countFollowers(userId);
    }

    @Override
    public List<UsersViewDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UsersViewDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UsersViewDTO userDTO = new UsersViewDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setFollowersCount(getFollowersCount(user.getId()));
            userDTO.setPostCount(user.getPostCount());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public List<UsersViewDTO> searchUsers(String firstName, String lastName, String email, String minPosts, String maxPosts, String sortBy, String sortDirection) {
        List<User> users = userRepository.findByCriteria(firstName, lastName, email);
        if (minPosts != null || maxPosts != null) {
            int parsedMinPosts = (minPosts != null && !minPosts.isEmpty()) ? Integer.parseInt(minPosts) : 0;
            int parsedMaxPosts = (maxPosts != null && !maxPosts.isEmpty()) ? Integer.parseInt(maxPosts) : Integer.MAX_VALUE;

            users = users.stream()
                    .filter(user -> {
                        int postCount = user.getPostCount();
                        boolean validMinPosts = postCount >= parsedMinPosts;
                        boolean validMaxPosts = postCount <= parsedMaxPosts;
                        return validMinPosts && validMaxPosts;
                    })
                    .collect(Collectors.toList());
        }
        List<UsersViewDTO> userDTOs = new ArrayList<>();
        if(sortBy == "" || sortBy == null)
            sortBy = "followersCount";
        if(sortDirection == "" || sortDirection == null)
            sortDirection = "ASC";
        Comparator<User> comparator;
        if ("followersCount".equals(sortBy)) {
            comparator = Comparator.comparing(user -> getFollowersCount(user.getId()));
        } else if ("email".equals(sortBy)) {
            comparator = Comparator.comparing(User::getEmail);
        } else {
            comparator = Comparator.comparing(User::getId); // Default sort by ID
        }

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }

        users.sort(comparator);

        for (User user : users) {
            UsersViewDTO userDTO = new UsersViewDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setFollowersCount(getFollowersCount(user.getId()));
            userDTO.setPostCount(user.getPostCount());
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }
}
