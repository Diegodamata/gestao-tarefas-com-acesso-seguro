package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.role.requests.RoleRequestDto;
import com.diegodev.taskmanager.controllers.dtos.role.responses.RoleResponseDto;
import com.diegodev.taskmanager.controllers.mappers.role.RoleMapper;
import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> created(@RequestBody RoleRequestDto roleRequestDto){

        Role role = roleService.created(roleMapper.toEntity(roleRequestDto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(uri).body(roleMapper.toDto(role));
    }
}
