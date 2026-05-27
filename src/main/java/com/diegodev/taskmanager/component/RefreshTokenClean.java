package com.diegodev.taskmanager.component;

import com.diegodev.taskmanager.repositories.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenClean {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenClean(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    //vai estar agendado para toda as 2 da manha
    //a deleção de refresh token revogado ou exirado
    //      0           0               2            *              *                       *
    //segundo(0-59), minuto(0-59), hora(0-23), dia do mes(1-31), mes(1-12), dia da semana(0-7, 0 e 7 = domingo)
    @Scheduled(cron = "0 0 2 * * *")
    public void cleanRefreshTokenRevokedOrExpired(){
        refreshTokenRepository.deleteByRevokedTrue();
        refreshTokenRepository.deleteByExpiresInBefore(Instant.now());
    }
}
