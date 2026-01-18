package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.mappers.UserMapper;
import com.diegodev.taskmanager.controllers.dtos.reponses.UserResponseDto;
import com.diegodev.taskmanager.controllers.dtos.reponses.UserUpdateResponseDto;
import com.diegodev.taskmanager.controllers.dtos.requests.UserRequestDto;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> created(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto dto = userMapper
                .toDto(userService.created(userMapper.toEntity(userRequestDto)));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/users")
                .buildAndExpand(dto.id())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> read(){
        List<UserResponseDto> listDto = userMapper
                .toListDto(userService.read());

        return ResponseEntity.ok().body(listDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> update(@RequestBody UserRequestDto userRequestDto,
                                                        @PathVariable("id") Long id){

        User userUpdate = userService
                .update(userMapper.toEntity(userRequestDto), id);

        return ResponseEntity.ok().body(userMapper
                .toUpdateDto(userUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
