package com.diegodev.taskmanager.controllers.dtos.user.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRequestDto(

        @NotBlank(message = "Campo login não pode estar vazio!")
        String login,

        @NotBlank(message = "Campo email não pode estar vazio!")
        @Email
        String email,

        @NotBlank(message = "Campo senha não pode estar vazio!")
        String password,

        @NotNull(message = "Informe role(s) do usuário!")
        Set<String> role_name
) {
}
