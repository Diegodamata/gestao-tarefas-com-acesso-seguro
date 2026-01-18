package com.diegodev.taskmanager.controllers.mappers.task;

import com.diegodev.taskmanager.controllers.dtos.task.requests.TaskRequestDto;
import com.diegodev.taskmanager.controllers.dtos.task.responses.TaskResponseDto;
import com.diegodev.taskmanager.domain.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto toDto(Task task);

    List<TaskResponseDto> toListDto(List<Task> tasks);
}
