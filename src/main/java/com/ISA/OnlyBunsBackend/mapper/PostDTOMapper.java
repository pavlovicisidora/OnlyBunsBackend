package com.ISA.OnlyBunsBackend.mapper;

import org.modelmapper.ModelMapper;
import com.ISA.OnlyBunsBackend.dto.PostDTO;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public PostDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Post fromDTOtoPost(PostDTO dto) {
        return modelMapper.map(dto, Post.class);
    }

    public static PostDTO fromPostToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
