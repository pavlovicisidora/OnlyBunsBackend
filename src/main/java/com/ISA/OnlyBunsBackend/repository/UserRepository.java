package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) FROM User u JOIN u.followers f WHERE f.id = :userId")
    Integer countFollowers(@Param("userId") Integer userId);

    @Query("SELECT u FROM User u WHERE " +
            "(:firstName IS NULL OR LOWER(CAST(u.firstName AS string)) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(CAST(u.lastName AS string)) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:email IS NULL OR LOWER(CAST(u.email AS string)) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:minPosts IS NULL OR :maxPosts IS NULL OR SIZE(u.posts) BETWEEN :minPosts AND :maxPosts)")
    List<User> findByCriteria(@Param("firstName") String firstName,
                              @Param("lastName") String lastName,
                              @Param("email") String email,
                              @Param("minPosts") Integer minPosts,
                              @Param("maxPosts") Integer maxPosts);
}
