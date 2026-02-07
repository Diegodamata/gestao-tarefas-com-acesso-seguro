package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        User user = userService.findByLogin(authentication.getName());
        String passwordDecoded = authentication.getCredentials().toString();

        boolean senhasBatem = passwordEncoder.matches(passwordDecoded, user.getPassword());
        
        if (senhasBatem){
            new CustomAuthentication(user);
        }

        throw getUserNotFound();
    }

    private UsernameNotFoundException getUserNotFound() {
        return new UsernameNotFoundException("Usuário ou Senha iválidos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
