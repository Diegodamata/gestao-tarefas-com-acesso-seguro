package com.diegodev.taskmanager.controllers.dtos.task.requests;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(

        @NotBlank(message = "Campo titulo não pode estar vazio!")
        String title,

        String description
) {
}
