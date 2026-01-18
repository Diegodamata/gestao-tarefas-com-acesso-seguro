package com.diegodev.taskmanager.controllers.dtos.user.responses;

public record UserUpdateResponseDto(
        String name,
        String email,
        String password
) {
}
