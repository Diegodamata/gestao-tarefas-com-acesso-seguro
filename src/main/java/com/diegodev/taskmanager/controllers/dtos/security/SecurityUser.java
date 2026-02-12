package com.diegodev.taskmanager.controllers.dtos.security;

import java.util.Set;

public record SecurityUser(
        Long id,
        String login,
        Set<String> roles
) {
}
