package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.task.requests.TaskRequestDto;
import com.diegodev.taskmanager.controllers.dtos.task.responses.TaskResponseDto;
import com.diegodev.taskmanager.controllers.mappers.task.TaskMapper;
import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.enums.Status;
import com.diegodev.taskmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    @Operation(summary = "Criar", description = "Criar uma nova task")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Task já cadastrada")
    })
    public ResponseEntity<TaskResponseDto> created(@RequestBody @Valid TaskRequestDto taskRequestDto){

        Task task = taskService
                .created(taskMapper.toEntity(taskRequestDto));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).body(taskMapper.toDto(task));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Buscar", description = "Buscar task por status")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Tasks listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada")
    })
    public ResponseEntity<Page<TaskResponseDto>> readingByTasksOrStatus(@RequestParam(required = false) String status,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){

        Page<Task> pageTasks;
        if (status != null && !status.isBlank()){
            pageTasks = taskService.findByStatus(Status.from(status), page, size);
        }
        else {
            pageTasks = taskService.readingByTasks(page, size);
        }

        return ResponseEntity.ok().body(pageTasks.map(taskMapper::toDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/titles")
    @Operation(summary = "Buscar", description = "Buscar task por title")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Tasks listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Task não encontrada")
    })
    public ResponseEntity<TaskResponseDto> readingByTitle(@RequestParam(value = "title", required = true) String title){


         Task task = taskService.findByTitle(title);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{task_id}")
    @Operation(summary = "Atualizar", description = "Atualizar uma task")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Tasks atualizada com sucesso")
    })
    public ResponseEntity<TaskResponseDto> update(@RequestBody @Valid TaskRequestDto taskRequestDto,
                                                        @PathVariable("task_id") Long task_id){

        Task task = taskService.update(taskMapper
                .toEntity(taskRequestDto), task_id);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("/{task_id}")
    @Operation(summary = "Concluir", description = "Concluir uma task")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Tasks concluida com sucesso")
    })
    public ResponseEntity<TaskResponseDto> concluirTask(@PathVariable("task_id") Long task_id){
        Task task = taskService.concluirTask(task_id);

        return ResponseEntity.ok().body(taskMapper.toDto(task));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{task_id}")
    @Operation(summary = "Deletar", description = "Deletar uma task")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "Tasks deletada com sucesso")
    })
    public ResponseEntity<Void> delete(@PathVariable("task_id") Long task_id){
        taskService.delete(task_id);
        return ResponseEntity.ok().build();
    }
}
