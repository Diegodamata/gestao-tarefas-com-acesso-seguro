package com.diegodev.taskmanager.validator;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.exceptions.RegistroDuplicadoException;
import com.diegodev.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    private final TaskRepository taskRepository;

    public TaskValidator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void validar(String title, User user){
        if (existsTask(title, user)){
            throw new RegistroDuplicadoException("Task já existente com esse nome!");
        }
    }

    private boolean existsTask(String title, User user) {
        return taskRepository.existsByTitleContainingIgnoreCaseAndUser(title, user);
    }
}
