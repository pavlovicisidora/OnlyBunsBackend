package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Post;

import java.util.List;

public interface PostService {
    List<PostViewDTO> getAllPosts();
    Post likePost(Integer postId, Integer userId);
    Comment addComment(Integer postId, Integer userId, String content);
    Post updatePost(Integer postId, Integer userId, String newDescription, String newImage);
    void deletePost(Integer postId, Integer userId);
}
