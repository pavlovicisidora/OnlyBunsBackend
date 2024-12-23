package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = """
    SELECT
        CASE
            WHEN :format = 'weekly' THEN TO_CHAR(c.created_at, 'IYYY-IW')
            WHEN :format = 'monthly' THEN TO_CHAR(c.created_at, 'YYYY-MM')
            WHEN :format = 'yearly' THEN TO_CHAR(c.created_at, 'YYYY')
        END AS format,
        COUNT(*) AS commentCount
    FROM comment c
    WHERE c.created_at BETWEEN :startDate AND :endDate
    GROUP BY format
    ORDER BY format
    """, nativeQuery = true)
    List<Object[]> countCommentsByInterval(@Param("format") String format, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT MIN(CAST(created_at AS DATE)) FROM comment c", nativeQuery = true)
    Optional<LocalDate> findEarliestCommentDate();

    @Query("SELECT COUNT(DISTINCT c.user.id) FROM Comment c")
    long countDistinctUsersWithComments();
    
    @Query(value = """
    SELECT COUNT(c.id) <= 60
    FROM comment c
    WHERE c.user_id = :userId
      AND c.created_at > NOW() - INTERVAL '1 hour'
    """, nativeQuery = true)
    boolean canUserCommentPost(@Param("userId") int userId);
}
