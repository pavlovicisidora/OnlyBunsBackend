package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.dto.PostViewDTO;

import java.util.List;

public interface PostService {
    List<PostViewDTO> getAllPosts();
}
