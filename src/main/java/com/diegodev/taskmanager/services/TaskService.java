package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.domain.enums.Status;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import com.diegodev.taskmanager.repositories.TaskRepository;
import com.diegodev.taskmanager.validator.TaskValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskValidator taskValidator;

    public TaskService(TaskRepository taskRepository, UserService userService, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskValidator = taskValidator;
    }

    public Task created(Task task, Long id){
        User user = userService.findById(id);

        taskValidator.validar(task.getTitle(), user);

        task.setUser(user);
        return taskRepository.save(task);
    }

    public Page<Task> readingByTasks(Long id, int page, int size){
        User user = userService.findById(id);

        return taskRepository.findByUser(user, PageRequest.of(page, size));
    }

    public Task findByTitle(String title, Long id){
        User user = userService.findById(id);

        return taskRepository.findByTitleContainingIgnoreCaseAndUser(title, user)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tarefa não encontrada com esse título: " + title));
    }

    public Page<Task> findByStatus(Status status, Long id, int page, int size){
        User user = userService.findById(id);

        return taskRepository.findByStatusAndUser(status, user, PageRequest.of(page, size));
    }

    public Task update(Task task, Long id, Long task_id){
        User user = userService.findById(id);

        Task taskEncontrada = user.getTasks().stream().
                filter(t -> t.getId().equals(task_id))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tarefa não encontrada!"));

        updateTask(task, taskEncontrada);

        return taskRepository.save(taskEncontrada);
    }

    private void updateTask(Task task, Task taskEncontrada) {
        if (task.getTitle() != null && !task.getTitle().equals(taskEncontrada.getTitle())) taskEncontrada.setTitle(task.getTitle());
        if (task.getDescription() != null && !task.getDescription().equals(taskEncontrada.getDescription())) taskEncontrada.setDescription(task.getDescription());
        if (task.getStatus() != null && !task.getStatus().equals(taskEncontrada.getStatus())) taskEncontrada.setStatus(task.getStatus());
    }

    public void delete(Long id, Long task_id){
        User user = userService.findById(id);

        Task taskEncontrada = user.getTasks().stream().
                filter(t -> t.getId().equals(task_id))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tarefa não encontrada!"));

        taskRepository.delete(taskEncontrada);
    }
}
