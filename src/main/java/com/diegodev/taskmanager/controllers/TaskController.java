package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.task.requests.TaskRequestDto;
import com.diegodev.taskmanager.controllers.dtos.task.responses.TaskResponseDto;
import com.diegodev.taskmanager.controllers.mappers.task.TaskMapper;
import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<TaskResponseDto> created(@RequestBody TaskRequestDto taskRequestDto, @PathVariable("id") Long id){

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
    public ResponseEntity<List<TaskResponseDto>> read(@PathVariable("id") Long id){
        List<Task> tasks = taskService.read(id);

        return ResponseEntity.ok().body(taskMapper.toListDto(tasks));
    }

    @GetMapping
    public ResponseEntity<TaskResponseDto> findByTitle(@RequestParam(value = "title", required = false) String title,
                                                       @PathVariable("id") Long id){
        Task task = taskService.findByTitle(title, id);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @PutMapping("/{task_id}")
    public ResponseEntity<TaskResponseDto> update(@RequestBody TaskRequestDto taskRequestDto,
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
