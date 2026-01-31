package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.role.requests.RoleRequestDto;
import com.diegodev.taskmanager.controllers.dtos.role.responses.RoleResponseDto;
import com.diegodev.taskmanager.controllers.mappers.role.RoleMapper;
import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> reading(){

        List<Role> roles = roleService.reading();

        return ResponseEntity.ok().body(roleMapper.toListDto(roles));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> update(@RequestBody RoleRequestDto roleRequestDto,
                                                  @PathVariable("id") Long id){
        Role roleUpdate = roleService.update(roleMapper.toEntity(roleRequestDto), id);

        return ResponseEntity.ok().body(roleMapper.toDto(roleUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
