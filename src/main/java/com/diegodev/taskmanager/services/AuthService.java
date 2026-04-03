package com.diegodev.taskmanager.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;

    public AuthService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generatedToken(Authentication authentication){

        Instant now = Instant.now();
        long expired = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("gestao-taredfas-com-acesso-seguro")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expired))
                .subject(authentication.getName())
                .claim("scopes", authentication.getAuthorities())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
