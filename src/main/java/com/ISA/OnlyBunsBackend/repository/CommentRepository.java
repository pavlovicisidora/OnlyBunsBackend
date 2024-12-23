package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = """
    SELECT COUNT(c.id) <= 60
    FROM comment c
    WHERE c.user_id = :userId
      AND c.created_at > NOW() - INTERVAL '1 hour'
    """, nativeQuery = true)
    boolean canUserCommentPost(@Param("userId") int userId);
}
