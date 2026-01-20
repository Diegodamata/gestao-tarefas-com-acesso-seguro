package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitleContainingIgnoreCaseAndUser(String title, User user);

    Page<Task> findAll(List<Task> tasks, Pageable pageable);
}
