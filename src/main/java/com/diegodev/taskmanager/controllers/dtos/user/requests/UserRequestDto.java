package com.diegodev.taskmanager.controllers.dtos.user.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(

        @NotBlank(message = "Campo nome não pode estar vazio!")
        String name,

        @Email(message = "Campo email não pode estar vazio!")
        String email,

        @NotBlank(message = "Campo senha não pode estar vazio!")
        String password
) {
}
