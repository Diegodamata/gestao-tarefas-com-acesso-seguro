package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.services.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    public String generatedToken(Authentication authentication){
        return authService.generatedToken(authentication);
    }
}
