package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task created(Task task, Long id){
        User user = userService.findById(id);

        task.setUser(user);

        return taskRepository.save(task);
    }

    public List<Task> read(Long id){
        User user = userService.findById(id);

        return user.getTasks();
    }

    public Task update(Task task, Long id, Long task_id){
        User user = userService.findById(id);

        Task taskEncontrada = user.getTasks().stream().
                filter(t -> t.getId().equals(task_id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        updateTask(task, taskEncontrada);

        return taskRepository.save(taskEncontrada);
    }

    private void updateTask(Task task, Task taskEncontrada) {
        if (task.getTitle() != null && !task.getTitle().equals(taskEncontrada.getTitle())) taskEncontrada.setTitle(task.getTitle());
        if (task.getDescription() != null && !task.getDescription().equals(taskEncontrada.getDescription())) taskEncontrada.setDescription(task.getDescription());
        if (task.getStatus() != null && !task.getStatus().equals(taskEncontrada.getStatus())) taskEncontrada.setStatus(task.getStatus());
    }
}
