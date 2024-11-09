package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) FROM User u JOIN u.followers f WHERE f.id = :userId")
    Integer countFollowers(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(:firstName IS NULL OR LOWER(u.first_name) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:minPosts IS NULL OR :maxPosts IS NULL OR (SELECT COUNT(*) FROM post p WHERE p.user_id = u.id) BETWEEN :minPosts AND :maxPosts)",
            nativeQuery = true)
    List<User> findByCriteria(@Param("firstName") String firstName,
                              @Param("lastName") String lastName,
                              @Param("email") String email,
                              @Param("minPosts") Integer minPosts,
                              @Param("maxPosts") Integer maxPosts);
}
