package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
