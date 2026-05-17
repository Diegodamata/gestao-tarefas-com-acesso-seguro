package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.controllers.dtos.login.LoginResponse;
import com.diegodev.taskmanager.domain.RefreshToken;
import com.diegodev.taskmanager.repositories.RefreshTokenRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, RefreshTokenRepository refreshTokenRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generatedAccessToken(Authentication authentication){

        Instant now = Instant.now();
        long expiresIn = 7200L;

        var scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var claims = JwtClaimsSet.builder()
                .issuer("gestao-tarefas-com-acesso-seguro")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generatedRefreshToken(String name){
        Instant now = Instant.now();
        long expiresIn = 864000L;

        var claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer("gestao-tarefas-com-acesso-seguro")
                .subject(name)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setName(name);
        refreshToken.setToken(token);
        refreshToken.setExpiresIn(now.plusSeconds(expiresIn));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public LoginResponse generatedAccessTokenViaRefresh(String refreshToken){

        Jwt jwt = jwtDecoder.decode(refreshToken);

        String name = jwt.getSubject();

        RefreshToken refreshTokenEncontrado
                = refreshTokenRepository.findByToken(refreshToken).orElseThrow();

        if (refreshTokenEncontrado.isRevoked()){
            throw new RuntimeException("Refresh Token revogado!");
        }

        if (refreshTokenEncontrado.getExpiresIn().isBefore(Instant.now())){
            throw new RuntimeException("Refresh Token expirado!");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                name,
                null,
                List.of());

        String accessToken = generatedAccessToken(authentication);

        String refresh = generatedRefreshToken(name);

        refreshTokenEncontrado.setRevoked(true);

        refreshTokenRepository.save(refreshTokenEncontrado);

        return new LoginResponse(accessToken, refreshToken, 7200L);
    }
}
