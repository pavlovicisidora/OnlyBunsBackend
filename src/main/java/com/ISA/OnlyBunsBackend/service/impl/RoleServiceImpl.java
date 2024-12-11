package com.ISA.OnlyBunsBackend.service.impl;

import java.util.List;

import com.ISA.OnlyBunsBackend.model.Role;
import com.ISA.OnlyBunsBackend.repository.RoleRepository;
import com.ISA.OnlyBunsBackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role findById(Long id) {
    Role auth = this.roleRepository.getReferenceById(id);
    return auth;
  }

  @Override
  public Role findByName(String name) {
	Role roles = this.roleRepository.findByName(name);
    return roles;
  }


}
