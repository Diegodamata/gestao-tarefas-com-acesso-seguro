package com.diegodev.taskmanager.controllers.dtos.task.responses;

import com.diegodev.taskmanager.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TaskResponse")
public record TaskResponseDto(
        Long id,
        String title,
        String description,
        Status status
) {
}
