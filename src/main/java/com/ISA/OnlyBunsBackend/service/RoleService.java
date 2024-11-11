package com.ISA.OnlyBunsBackend.service;

import com.ISA.OnlyBunsBackend.model.Role;

import java.util.List;

public interface RoleService {
	Role findById(Long id);
	Role findByName(String name);
}
