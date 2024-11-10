package com.ISA.OnlyBunsBackend.controller;


import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
