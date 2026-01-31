package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginAndEmailAndPassword(String login, String email, String password);
}
