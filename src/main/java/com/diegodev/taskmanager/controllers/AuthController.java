package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.login.LoginResponse;
import com.diegodev.taskmanager.controllers.dtos.token.RefreshToken;
import com.diegodev.taskmanager.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(Authentication authentication){

        String accessToken = authService.generatedAccessToken(authentication);

        String refreshToken = authService.generatedRefreshToken(authentication.getName());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, 3600L));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshToken refreshToken){
        String refresh = refreshToken.refreshToken();
        return ResponseEntity.ok(
                authService.generatedAccessTokenViaRefresh(refresh));
    }
}
