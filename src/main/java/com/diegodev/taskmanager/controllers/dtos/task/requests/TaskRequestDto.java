package com.diegodev.taskmanager.controllers.dtos.task.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "TaskRequest")
public record TaskRequestDto(

        @NotBlank(message = "Campo titulo não pode estar vazio!")
        String title,

        String description
) {
}
