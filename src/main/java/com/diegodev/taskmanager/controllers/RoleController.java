package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.role.requests.RoleRequestDto;
import com.diegodev.taskmanager.controllers.dtos.role.responses.RoleResponseDto;
import com.diegodev.taskmanager.controllers.mappers.role.RoleMapper;
import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar", description = "Criar uma role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrando uma nova role"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Role já cadastrada")
    })
    public ResponseEntity<RoleResponseDto> created(@RequestBody @Valid RoleRequestDto roleRequestDto){

        Role role = roleService.created(roleMapper.toEntity(roleRequestDto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(uri).body(roleMapper.toDto(role));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar", description = "Listar todas as roles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles listadas com sucesso")
    })
    public ResponseEntity<List<RoleResponseDto>> reading(){

        List<Role> roles = roleService.reading();

        return ResponseEntity.ok().body(roleMapper.toListDto(roles));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar", description = "Atualizar uma role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Role não encontrada")
    })
    public ResponseEntity<RoleResponseDto> update(@RequestBody @Valid RoleRequestDto roleRequestDto,
                                                  @PathVariable("id") Long id){
        Role roleUpdate = roleService.update(roleMapper.toEntity(roleRequestDto), id);

        return ResponseEntity.ok().body(roleMapper.toDto(roleUpdate));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deletando uma role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }
}
