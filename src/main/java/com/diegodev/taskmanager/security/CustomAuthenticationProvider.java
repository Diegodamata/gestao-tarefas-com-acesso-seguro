package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.exceptions.ForbiddenException;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        User user = userService.findByLogin(authentication.getName());
        if (user == null){
            throw new ForbiddenException("Usuário e/ou Senha inválidos!");
        }
        String passwordDecoded = authentication.getCredentials().toString();

        boolean senhasBatem = passwordEncoder.matches(passwordDecoded, user.getPassword());

        if (!senhasBatem) {
            throw new ForbiddenException("Usuário e/ou Senha inválidos!");
        }

        User userComRoles = userService.findByLoginFetchRoles(user.getLogin());

        return new CustomAuthentication(userComRoles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
