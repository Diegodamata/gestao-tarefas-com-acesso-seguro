package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.domain.enums.Status;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import com.diegodev.taskmanager.repositories.TaskRepository;
import com.diegodev.taskmanager.security.ObterUserLogado;
import com.diegodev.taskmanager.validator.TaskValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    private final ObterUserLogado obterUserLogado;

    public TaskService(TaskRepository taskRepository, TaskValidator taskValidator, ObterUserLogado obterUserLogado) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.obterUserLogado = obterUserLogado;
    }

    public Task created(Task task){
        User user = obterUserLogado.getUserLogado();
        taskValidator.validar(task.getTitle(), user);
        task.setStatus(Status.PENDENTE);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Page<Task> readingByTasks(int page, int size){
        User user = obterUserLogado.getUserLogado();
        return taskRepository.findByUser(user, PageRequest.of(page, size));
    }

    public Task findByTitle(String title){
        User user = obterUserLogado.getUserLogado();
        return taskRepository.findByTitleContainingIgnoreCaseAndUser(title, user)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tarefa não encontrada com esse título: " + title));
    }

    public Page<Task> findByStatus(Status status, int page, int size){
        User user = obterUserLogado.getUserLogado();
        return taskRepository.findByStatusAndUser(status, user, PageRequest.of(page, size));
    }

    public Task update(Task task, Long task_id){
        User user = obterUserLogado.getUserLogado();
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

    public void delete(Long task_id){
        User user = obterUserLogado.getUserLogado();
        Task taskEncontrada = user.getTasks().stream().
                filter(t -> t.getId().equals(task_id))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Tarefa não encontrada!"));

        taskRepository.delete(taskEncontrada);
    }
}
