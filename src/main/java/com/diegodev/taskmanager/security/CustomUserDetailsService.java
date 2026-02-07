package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userService.findByLogin(login);

        return new CustomUserDetais(user);
    }
}
