package com.diegodev.taskmanager.controllers.dtos.user.responses;

public record UserUpdateResponseDto(
        String login,
        String email,
        String password
) {
}
