package com.ISA.OnlyBunsBackend.repository;

import java.util.List;

import com.ISA.OnlyBunsBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
