package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.dto.UserDTO;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) Integer minPosts,
                                  @RequestParam(required = false) Integer maxPosts) {
        return userService.searchUsers(firstName, lastName, email, minPosts, maxPosts);
    }
}
