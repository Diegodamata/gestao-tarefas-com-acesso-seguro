package com.diegodev.taskmanager.controllers.dtos.task.requests;

import com.diegodev.taskmanager.domain.enums.Status;

public record TaskRequestDto(
        String title,
        String description,
        Status status
) {
}
