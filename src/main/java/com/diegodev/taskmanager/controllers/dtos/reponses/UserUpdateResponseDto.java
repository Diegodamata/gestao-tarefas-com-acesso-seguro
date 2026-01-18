package com.diegodev.taskmanager.controllers.dtos.reponses;

public record UserUpdateResponseDto(
        String name,
        String email,
        String password
) {
}
