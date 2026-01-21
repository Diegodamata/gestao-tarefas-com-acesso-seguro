package com.diegodev.taskmanager.controllers.dtos.task.requests;

import com.diegodev.taskmanager.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(

        @NotBlank(message = "Campo titulo não pode estar vazio!")
        String title,
        String description,
        Status status
) {
}
