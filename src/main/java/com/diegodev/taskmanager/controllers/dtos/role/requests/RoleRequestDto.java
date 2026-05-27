package com.diegodev.taskmanager.controllers.dtos.role.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "RoleRequest")
public record RoleRequestDto(
        @NotBlank(message = "Campo name não pode estar vazio!")
        String name
) {
}
