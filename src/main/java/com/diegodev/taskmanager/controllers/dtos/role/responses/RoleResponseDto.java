package com.diegodev.taskmanager.controllers.dtos.role.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RoleResponse")
public record RoleResponseDto(
        Long id,
        String name
) {
}
