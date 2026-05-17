package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
