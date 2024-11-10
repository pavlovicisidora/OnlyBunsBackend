package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
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
    public List<UsersViewDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public List<UsersViewDTO> searchUsers(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String minPosts,
                                  @RequestParam(required = false) String maxPosts,
                                  @RequestParam(required = false) String sortBy,
                                  @RequestParam(required = false) String sortDirection) {
        return userService.searchUsers(firstName, lastName, email, minPosts, maxPosts, sortBy, sortDirection);
    }
}
