package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
