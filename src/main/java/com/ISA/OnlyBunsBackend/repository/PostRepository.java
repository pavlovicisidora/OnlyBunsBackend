package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository  extends JpaRepository<Post, Integer> {
}
