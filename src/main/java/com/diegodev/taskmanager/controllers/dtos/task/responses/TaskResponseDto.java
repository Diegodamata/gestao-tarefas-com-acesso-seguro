package com.diegodev.taskmanager.controllers.dtos.task.responses;

import com.diegodev.taskmanager.domain.enums.Status;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        Status status
) {
}
