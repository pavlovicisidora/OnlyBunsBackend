package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.UserDTO;
import com.ISA.OnlyBunsBackend.mapper.UserDTOMapper;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
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
    public List<UserDTO> searchUsers(String firstName, String lastName, String email, Integer minPosts, Integer maxPosts) {
        List<User> users = userRepository.findByCriteria(firstName, lastName, email, minPosts, maxPosts);
        return users.stream()
                .map(UserDTOMapper::fromUserToDTO)
                .collect(Collectors.toList());
    }
}
