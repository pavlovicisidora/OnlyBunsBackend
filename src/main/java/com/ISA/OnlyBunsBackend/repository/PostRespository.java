package com.ISA.OnlyBunsBackend.repository;
import com.ISA.OnlyBunsBackend.model.Comment;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRespository extends JpaRepository<Post,Integer> {

    public Post findOneByIndex(int id);

    public Page<Post> findAll(Pageable pageable);

    public List<Comment> getAllPostComment(int id);

    public Integer  getAllPostLikes(int id);



}
