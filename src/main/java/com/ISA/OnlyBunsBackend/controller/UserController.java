package com.ISA.OnlyBunsBackend.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import org.springframework.web.bind.annotation.RequestParam;

// Primer kontrolera cijim metodama mogu pristupiti samo autorizovani korisnici
@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsersViewDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userInfo")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }

    @GetMapping("/profile/{id}")
    public UsersViewDTO getUserInfo(@PathVariable int id) {
        return this.userService.getUser(id);
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
