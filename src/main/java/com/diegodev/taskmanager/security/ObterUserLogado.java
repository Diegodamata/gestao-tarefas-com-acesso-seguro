package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ObterUserLogado {

    private final UserService userService;

    public ObterUserLogado(UserService userService) {
        this.userService = userService;
    }

    public User getUserLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String login = authentication.getName();

        return userService.findByLogin(login);
    }
}
