package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT COUNT(*) FROM Post", nativeQuery = true)
    int countTotalPosts();


    @Query(value = "SELECT COUNT(*) FROM Post p WHERE p.time_of_publishing >= :startDate", nativeQuery = true)
    long countPostsInLastMonth(@Param("startDate") LocalDate startDate);

    @Query(value = """
    SELECT p.* 
    FROM post p 
    LEFT JOIN post_user_likes pul ON p.id = pul.post_id 
    WHERE p.time_of_publishing >= :startDate AND p.is_deleted = false
    GROUP BY p.id 
    ORDER BY COUNT(pul.user_id) DESC 
    LIMIT 5
    """, nativeQuery = true)
    List<Post> findTop5MostLikedPostsInLast7Days(@Param("startDate") LocalDate startDate);


    @Query(value = """
    SELECT p.* 
    FROM post p 
    LEFT JOIN post_user_likes pul ON p.id = pul.post_id 
    WHERE p.is_deleted = false
    GROUP BY p.id 
    ORDER BY COUNT(pul.user_id) DESC 
    LIMIT 10
    """, nativeQuery = true)
    List<Post> findTop10MostLikedPostsOfAllTime();

}
