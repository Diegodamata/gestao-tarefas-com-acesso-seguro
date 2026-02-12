package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.controllers.dtos.security.SecurityUser;
import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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

        User user = userService.findByLoginFetchRoles(authentication.getName());
        String passwordDecoded = authentication.getCredentials().toString();

        boolean senhasBatem = passwordEncoder.matches(passwordDecoded, user.getPassword());

        if (senhasBatem) {

            Set<String> roles = user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            SecurityUser securityUser = new SecurityUser(
                    user.getId(),
                    user.getLogin(),
                    roles
            );

            return new CustomAuthentication(securityUser);
        }

        throw new UsernameNotFoundException("Usuário e/ou Senha inválidos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
