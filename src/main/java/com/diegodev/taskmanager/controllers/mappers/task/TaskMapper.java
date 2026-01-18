package com.diegodev.taskmanager.controllers.mappers.task;

import com.diegodev.taskmanager.controllers.dtos.task.requests.TaskRequestDto;
import com.diegodev.taskmanager.controllers.dtos.task.responses.TaskResponseDto;
import com.diegodev.taskmanager.domain.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto toDto(Task task);
}
