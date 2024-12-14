package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.CommentDTO;
import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.PostService;
import com.ISA.OnlyBunsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

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
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) {
        PostDTO newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
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
