package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.user.requests.UserRequestDto;
import com.diegodev.taskmanager.controllers.dtos.user.responses.UserResponseDto;
import com.diegodev.taskmanager.controllers.dtos.user.responses.UserUpdateResponseDto;
import com.diegodev.taskmanager.controllers.mappers.user.UserMapper;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
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
import java.util.Set;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @Operation(summary = "Criar", description = "Criar novo usuário")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    })
    public ResponseEntity<UserResponseDto> created(@RequestBody @Valid UserRequestDto userRequestDto){
        Set<String> role_name = userRequestDto.role_name();
        User user = userService
                .created(userMapper.toEntity(userRequestDto), role_name);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userMapper.toDto(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar", description = "Listar todos os usuarios")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    })
    public ResponseEntity<List<UserResponseDto>> read(){
        List<UserResponseDto> listDto = userMapper
                .toListDto(userService.read());
        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Atualizar", description = "Atualizar dados do usuário")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserUpdateResponseDto> update(@RequestBody @Valid UserRequestDto userRequestDto,
                                                        @PathVariable("id") Long id){

        User userUpdate = userService
                .update(userMapper.toEntity(userRequestDto), id);

        return ResponseEntity.ok().body(userMapper.toUpdateDto(userUpdate));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deletando um usuário")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
