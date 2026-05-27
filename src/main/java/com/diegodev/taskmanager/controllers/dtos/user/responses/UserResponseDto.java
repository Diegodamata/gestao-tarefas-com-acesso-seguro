package com.diegodev.taskmanager.controllers.dtos.user.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse")
public record UserResponseDto(
        Long id,
        String login
) {
}
