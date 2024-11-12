package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.CommentDTO;
import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/all")
    public List<PostViewDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        Post updatedPost = postService.likePost(postId, userId);
        return ResponseEntity.ok(updatedPost);
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Integer postId,
            @RequestParam Integer userId,
            @RequestParam String content) {
        Comment newComment = postService.addComment(postId, userId, content);
        return ResponseEntity.ok(new CommentDTO(newComment));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId,
            @RequestParam String newDescription,
            @RequestParam String newImage) {
        Post updatedPost = postService.updatePost(postId, userId, newDescription, newImage);
        return ResponseEntity.ok(updatedPost);
    }
    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) {
        PostDTO newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/isLiked")
    public boolean isLiked(@PathVariable Integer postId, @RequestParam Integer userId) {
        return postService.isLiked(postId, userId);
    }
}
