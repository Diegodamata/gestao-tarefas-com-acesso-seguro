package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.mappers.UserMapper;
import com.diegodev.taskmanager.controllers.dtos.requests.UserRequestDto;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void created(@RequestBody UserRequestDto userRequestDto){
        userService.created(userMapper.toEntity(userRequestDto));
    }
}
