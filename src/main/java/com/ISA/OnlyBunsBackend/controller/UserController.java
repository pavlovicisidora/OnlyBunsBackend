package com.ISA.OnlyBunsBackend.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsersViewDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @GetMapping("/userInfo")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }

    @GetMapping("/profile/{id}")
    public UsersViewDTO getUserInfo(@PathVariable int id) {
        return this.userService.getUser(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UsersViewDTO> searchUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size,
                                          @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) String minPosts,
                                          @RequestParam(required = false) String maxPosts,
                                          @RequestParam(required = false) String sortBy,
                                          @RequestParam(required = false) String sortDirection) {
        return userService.searchUsers(PageRequest.of(page, size), firstName, lastName, email, minPosts, maxPosts, sortBy, sortDirection);
    }

    @PutMapping("/follow/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsersViewDTO> followUser(@PathVariable Integer id, Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        return ResponseEntity.ok(userService.followUser(loggedInUser.getId(), id));
    }

    @DeleteMapping ("/unfollow/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsersViewDTO> unfollowUser(@PathVariable Integer id, Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        return ResponseEntity.ok(userService.unfollowUser(loggedInUser.getId(), id));
    }

    @GetMapping("/isFollowing/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Integer id, Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        boolean isFollowing = userService.isFollowing(loggedInUser.getId(), id);
        return ResponseEntity.ok(isFollowing);
    }

    @GetMapping("/following/{userId}")
    public List<UsersViewDTO> getFollowing(@PathVariable Integer userId) {
        return userService.getFollowingUsers(userId);
    }

    @GetMapping("/top-10-users-for-likes")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<UsersViewDTO>> getTopUsersWhoSharedMostLikesInLast7Days() {
        List<UsersViewDTO> topUsers = userService.getTop10UsersWhoSharedMostLikesInLast7Days();
        return ResponseEntity.ok(topUsers);
    }
}
