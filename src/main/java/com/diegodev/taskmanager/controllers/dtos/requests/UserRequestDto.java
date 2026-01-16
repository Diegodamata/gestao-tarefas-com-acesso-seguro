package com.diegodev.taskmanager.controllers.dtos.requests;

public record UserRequestDto(
        String name,
        String email,
        String password
) {
}
