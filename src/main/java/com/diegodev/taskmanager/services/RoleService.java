package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role created(Role role){
        role.setName(role.getName().toUpperCase());
        return roleRepository.save(role);
    }
}
