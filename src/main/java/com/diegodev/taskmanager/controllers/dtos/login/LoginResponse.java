package com.diegodev.taskmanager.controllers.dtos.login;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn) {
}
