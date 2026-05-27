package com.diegodev.taskmanager.controllers.dtos.user.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserUpdateResponse")
public record UserUpdateResponseDto(
        String login,
        String email,
        String password
) {
}
