package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.controllers.dtos.security.SecurityUser;
import com.diegodev.taskmanager.domain.Role;
import com.diegodev.taskmanager.domain.User;
import com.diegodev.taskmanager.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoginGoogleSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String passwordDefault = "123";

    private final UserService userService;

    public LoginGoogleSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        User userEncontrado = userService.findByEmailFetchRoles(email);

        if (userEncontrado == null){
            cadastrarUser(email);
        }

        Set<String> roles = userEncontrado.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        SecurityUser securityUser = new SecurityUser(
                userEncontrado.getId(),
                userEncontrado.getEmail(),
                roles);
        authentication = new CustomAuthentication(securityUser);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    private void cadastrarUser(String email) {
        User user = new User();
        user.setLogin(email.substring(0, email.indexOf("@")));
        user.setEmail(email);
        user.setPassword(passwordDefault);

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        userService.created(user, roles);
    }
}
