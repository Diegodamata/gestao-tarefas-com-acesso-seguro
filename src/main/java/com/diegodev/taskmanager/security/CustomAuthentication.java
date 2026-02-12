package com.diegodev.taskmanager.security;

import com.diegodev.taskmanager.controllers.dtos.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class CustomAuthentication implements Authentication {

    private final SecurityUser securityUser;

    public CustomAuthentication(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return securityUser.roles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return securityUser;
    }

    @Override
    public Object getPrincipal() {
        return securityUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return securityUser.login();
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
