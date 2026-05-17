package com.diegodev.taskmanager.validator;

import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.exceptions.RegistroDuplicadoException;
import com.diegodev.taskmanager.repositories.RoleRepository;

public class RoleValidator {

    private final RoleRepository roleRepository;

    public RoleValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void validar(Role role){
        if (exists(role)){
            throw new RegistroDuplicadoException("Role ja cadasdrada!");
        }
    }

    public boolean exists(Role role){
        return roleRepository
                .existsByName(role.getName());
    }
}
