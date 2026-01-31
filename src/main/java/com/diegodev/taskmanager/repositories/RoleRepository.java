package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
