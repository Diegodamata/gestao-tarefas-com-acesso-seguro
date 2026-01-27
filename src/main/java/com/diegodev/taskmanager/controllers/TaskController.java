package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.task.requests.TaskRequestDto;
import com.diegodev.taskmanager.controllers.dtos.task.responses.TaskResponseDto;
import com.diegodev.taskmanager.controllers.mappers.task.TaskMapper;
import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.enums.Status;
import com.diegodev.taskmanager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/{id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> created(@RequestBody @Valid TaskRequestDto taskRequestDto, @PathVariable("id") Long id){

        Task task = taskService
                .created(taskMapper.toEntity(taskRequestDto), id);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(taskMapper.toDto(task));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDto>> readingByTasksOrStatus(@PathVariable("id") Long id,
                                                      @RequestParam(required = false) String status,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){


        Page<Task> pageTasks;
        if (status != null && !status.isBlank()){
            pageTasks = taskService.findByStatus(Status.from(status), id, page, size);
        }
        else {
            pageTasks = taskService.readingByTasks(id, page, size);
        }

        return ResponseEntity.ok().body(pageTasks.map(taskMapper::toDto));
    }

    @GetMapping("/titles")
    public ResponseEntity<TaskResponseDto> readingByTitle(@PathVariable("id") Long id,
                                                       @RequestParam(value = "title", required = true) String title){


         Task task = taskService.findByTitle(title, id);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @PutMapping("/{task_id}")
    public ResponseEntity<TaskResponseDto> update(@RequestBody @Valid TaskRequestDto taskRequestDto,
                                                        @PathVariable("id") Long id,
                                                        @PathVariable("task_id") Long task_id){

        Task task = taskService.update(taskMapper
                .toEntity(taskRequestDto), id, task_id);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @DeleteMapping("/{task_id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                            @PathVariable("task_id") Long task_id){
        taskService.delete(id, task_id);
        return ResponseEntity.ok().build();
    }
}
