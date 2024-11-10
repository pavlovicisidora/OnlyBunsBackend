package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.dto.CommentDTO;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import com.ISA.OnlyBunsBackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

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
}
