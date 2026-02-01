package com.diegodev.taskmanager.controllers.dtos.role.requests;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDto(
        @NotBlank(message = "Campo name não pode estar vazio!")
        String name
) {
}
