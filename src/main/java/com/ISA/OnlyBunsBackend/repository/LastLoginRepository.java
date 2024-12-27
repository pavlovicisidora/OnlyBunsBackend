package com.ISA.OnlyBunsBackend.repository;


import com.ISA.OnlyBunsBackend.model.LastLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LastLoginRepository  extends JpaRepository<LastLogin, Integer> {
    LastLogin findByUserId(int userId);
}
