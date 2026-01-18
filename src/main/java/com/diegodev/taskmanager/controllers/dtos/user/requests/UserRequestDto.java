package com.diegodev.taskmanager.controllers.dtos.user.requests;

public record UserRequestDto(
        String name,
        String email,
        String password
) {
}
