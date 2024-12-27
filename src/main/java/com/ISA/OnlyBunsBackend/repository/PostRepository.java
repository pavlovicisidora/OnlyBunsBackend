package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.dto.PostViewDTO;
import com.ISA.OnlyBunsBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Query(value = """
    SELECT
        CASE
            WHEN :format = 'weekly' THEN TO_CHAR(p.time_of_publishing, 'IYYY-IW')
            WHEN :format = 'monthly' THEN TO_CHAR(p.time_of_publishing, 'YYYY-MM')
            WHEN :format = 'yearly' THEN TO_CHAR(p.time_of_publishing, 'YYYY')
        END AS format,
        COUNT(*) AS postCount
    FROM post p
    WHERE p.time_of_publishing BETWEEN :startDate AND :endDate
    GROUP BY format
    ORDER BY format
    """, nativeQuery = true)
    List<Object[]> countPostsByInterval(@Param("format") String format, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT MIN(CAST(time_of_publishing AS DATE)) FROM post p", nativeQuery = true)
    Optional<LocalDate> findEarliestPostDate();

    @Query("SELECT COUNT(DISTINCT p.user.id) FROM Post p")
    long countDistinctUsersWithPosts();
    @Query(value = """
    SELECT COUNT(pul.user_id)
    FROM post p
    LEFT JOIN post_user_likes pul ON p.id = pul.post_id
    WHERE p.user_id = :userId
    """, nativeQuery = true)
    int getUserPostsLikesCount(@Param("userId") int userId);


    @Query(value = """
    SELECT COUNT(c.user_id)
    FROM comment c
    LEFT JOIN post p ON p.id = c.post_id
    WHERE p.user_id = :userId
    """,nativeQuery = true)
    int getUserPostsCommentsCount(int userId);
}
