package com.diegodev.taskmanager.services;

import com.diegodev.taskmanager.domain.RefreshToken;
import com.diegodev.taskmanager.repositories.RefreshTokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(JwtEncoder jwtEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generatedAccessToken(Authentication authentication){

        Instant now = Instant.now();
        long expiresIn = 3600L;

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

        var jti = UUID.randomUUID().toString();

        var claims = JwtClaimsSet.builder()
                .id(jti)
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
}
