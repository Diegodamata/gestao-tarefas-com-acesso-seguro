package com.diegodev.taskmanager.controllers;

import com.diegodev.taskmanager.controllers.dtos.erros.ErroCampo;
import com.diegodev.taskmanager.controllers.dtos.erros.ErroResposta;
import com.diegodev.taskmanager.exceptions.RegistroDuplicadoException;
import com.diegodev.taskmanager.exceptions.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erroCampos);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflict(e.getMessage());
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleRegistroNaoEncontradoException(RegistroNaoEncontradoException e){
        return ErroResposta.notFound(e.getMessage());
    }
}
