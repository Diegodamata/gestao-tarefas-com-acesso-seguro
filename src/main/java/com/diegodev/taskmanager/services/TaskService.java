package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<Task> read(Long id, int page, int size){
        User user = userService.findById(id);
        List<Task> userTasks = user.getTasks();

        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findAll(userTasks, pageable);
    }

    public Task findByTitle(String title, Long id){
        User user = userService.findById(id);

        return taskRepository.findByTitleContainingIgnoreCaseAndUser(title, user)
                .orElseThrow(() -> new RuntimeException("Task not found!"));
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

    public void delete(Long id, Long task_id){
        User user = userService.findById(id);

        Task taskEncontrada = user.getTasks().stream().
                filter(t -> t.getId().equals(task_id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        taskRepository.delete(taskEncontrada);
    }
}
