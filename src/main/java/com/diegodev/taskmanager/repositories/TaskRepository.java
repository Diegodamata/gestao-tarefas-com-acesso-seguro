package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.Task;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitleContainingIgnoreCaseAndUser(String title, User user);

    Page<Task> findByStatusAndUser(Status status, User user, Pageable pageable);

    Page<Task> findByUser(User user, Pageable pageable);

    boolean existsByTitleContainingIgnoreCaseAndUser(String title, User user);
}
