package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task created(Task task){
        return taskRepository.save(task);
    }
}
