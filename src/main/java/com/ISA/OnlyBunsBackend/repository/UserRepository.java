package com.ISA.OnlyBunsBackend.repository;

import com.ISA.OnlyBunsBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
