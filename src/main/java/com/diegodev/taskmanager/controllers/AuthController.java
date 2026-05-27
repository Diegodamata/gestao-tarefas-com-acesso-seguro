package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.login.LoginResponse;
import com.diegodev.taskmanager.controllers.dtos.token.RefreshToken;
import com.diegodev.taskmanager.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Login", description = "Login do usuário")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "AcessToken e Refresh token retornado com sucesso")
    })
    public ResponseEntity<LoginResponse> login(Authentication authentication){

        String accessToken = authService.generatedAccessToken(authentication);

        String refreshToken = authService.generatedRefreshToken(authentication.getName());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, 3600L));
    }

    @PostMapping("/refresh")
    @Operation(summary = "RefreshToken", description = "Obtendo um novo accessToken atraves do refresh")
    @ApiResponses({ //pode retornar mais de uma resposta, recebe um array
            @ApiResponse(responseCode = "200", description = "AcessToken e Refresh token retornado com sucesso")
    })
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshToken refreshToken){
        String refresh = refreshToken.refreshToken();
        return ResponseEntity.ok(
                authService.generatedAccessTokenViaRefresh(refresh));
    }
}
