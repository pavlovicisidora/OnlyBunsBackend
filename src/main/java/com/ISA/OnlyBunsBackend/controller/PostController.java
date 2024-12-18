package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.*;
import com.ISA.OnlyBunsBackend.mapper.LocationDTOMapper;
import com.ISA.OnlyBunsBackend.mapper.UserDTOMapper;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.LocationService;
import com.ISA.OnlyBunsBackend.service.PostService;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationDTOMapper locationDTOMapper;


    @GetMapping
    public List<PostViewDTO> getPosts(Principal user) {
        User loggedInUser = this.userService.findByUsername(user.getName());
        if(loggedInUser.getRole().getId() == 1)
            return postService.getAllPosts();
        else
            return postService.getPostsByFollowedUsers(loggedInUser.getId());
    }

    @GetMapping("/{id}")
    public PostViewDTO getPostById(int id) {
        return postService.getPostById(id);
    }

    @PostMapping("/{postId}/like")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostViewDTO> likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        PostViewDTO updatedPost = postService.likePost(postId, userId);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping("/{postId}/comment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Integer postId,
            @RequestParam Integer userId,
            @RequestParam String content) {
        Comment newComment = postService.addComment(postId, userId, content);
        return ResponseEntity.ok(new CommentDTO(newComment));
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostViewDTO> updatePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId,
            @RequestParam String newDescription,
            @RequestParam String newImage) {
        PostViewDTO updatedPost = postService.updatePost(postId, userId, newDescription, newImage);
        return ResponseEntity.ok(updatedPost);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createPost(
            @RequestBody LocationDTO location,
            @RequestParam Integer userId,
            @RequestParam String postDescription,
            @RequestParam String postImage,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime postTimeOfPublishing
            ) {
        LocationDTO newLocation = locationService.createLocation(location);
        User user = userService.findById(userId);
        PostDTO post = new PostDTO(postTimeOfPublishing,LocationDTOMapper.fromDTOtoLocation(newLocation),postImage,postDescription,user,0);

        PostDTO newPost = postService.createPost(post);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/isLiked")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public boolean isLiked(@PathVariable Integer postId, @RequestParam Integer userId) {
        return postService.isLiked(postId, userId);
    }

    @GetMapping("/allUsersPosts/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<PostViewDTO> getAllUsersPosts(@PathVariable Integer userId) {
        return postService.getAllPostsByUserId(userId);
    }
}
