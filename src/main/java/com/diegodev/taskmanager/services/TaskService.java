package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

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
}
