package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.dto.CommentDTO;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.CommentRepository;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<PostViewDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostViewDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostViewDTO postDTO = new PostViewDTO();
            postDTO.setId(post.getId());
            postDTO.setDescription(post.getDescription());
            postDTO.setImage(post.getImage());
            postDTO.setLikeCount(post.getLikesCount());
            postDTO.setComments(post.getComments().stream()
                    .map(CommentDTO::new)
                    .toList());
            postDTO.setDeleted(post.isDeleted());
            if (!postDTO.isDeleted())
                postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    public Post likePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (post.getUserLikes().contains(user)) {
            post.getUserLikes().remove(user);
        } else {
            post.getUserLikes().add(user);
        }

        return postRepository.save(post);
    }

    public Comment addComment(Integer postId, Integer userId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(text);
        comment.setCreatedAt(LocalDateTime.now());

        post.getComments().add(comment);

        commentRepository.save(comment);
        postRepository.save(post);

        return comment;
    }

    public Post updatePost(Integer postId, Integer userId, String newDescription, String newImage) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            return null;
        }

        post.setDescription(newDescription);
        post.setImage(newImage);

        return postRepository.save(post);
    }

    public void deletePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            return;
        }

        post.setDeleted(true);
        postRepository.save(post);
    }


}