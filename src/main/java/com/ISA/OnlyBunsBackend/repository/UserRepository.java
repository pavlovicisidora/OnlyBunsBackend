package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u JOIN u.followers f WHERE f.id = :userId")
    Integer countFollowers(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(:firstName IS NULL OR LOWER(u.first_name) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))",
            nativeQuery = true)
    List<User> findByCriteria(@Param("firstName") String firstName,
                              @Param("lastName") String lastName,
                              @Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.isActivated = false AND u.lastPasswordResetDate < :createdAt")
    List<User> findByIsActivatedFalseAndCreatedAtBefore(@Param("createdAt") LocalDateTime createdAt);

    @Query("SELECT u FROM User u JOIN u.followers f WHERE f.id = :userId")
    List<User> findFollowingUsers(@Param("userId") Integer userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    public Optional<User> findOneById(@Param("id")Integer id);
}
