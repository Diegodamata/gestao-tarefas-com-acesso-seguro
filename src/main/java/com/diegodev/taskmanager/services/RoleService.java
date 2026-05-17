package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import com.diegodev.taskmanager.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Role> reading(){
        return roleRepository.findAll();
    }

    public Role findById(Long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Role não encontrada!"));
    }

    public Role findByName(String name){
        return roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Role não encontrada!"));
    }

    public Role update(Role role, Long id){
        Role roleEncontrada = findById(id);

        updateRole(roleEncontrada, role);

        return roleRepository.save(roleEncontrada);
    }

    private void updateRole(Role roleEncontrada, Role role){
        if (role.getName() != null) roleEncontrada.setName(role.getName().toUpperCase());
    }

    public void delete(Long id){
        roleRepository.delete(findById(id));
    }
}
