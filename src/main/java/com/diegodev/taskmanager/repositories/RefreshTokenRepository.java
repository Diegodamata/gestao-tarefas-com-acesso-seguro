package com.diegodev.taskmanager.repositories;

import com.diegodev.taskmanager.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String refreshToken);
    void deleteByRevokedTrue();
    void deleteByExpiresInBefore(Instant now);
}
