package com.diegodev.taskmanager.controllers.dtos.erros;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String message, List<ErroCampo> errors) {

    public static ErroResposta conflict(String message){
        return new ErroResposta(HttpStatus.CONFLICT.value(), message, List.of());
    }

    public static ErroResposta notFound(String message){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), message, List.of());
    }
}
