package com.diegodev.taskmanager.domain.enums;

public enum Status {
    PENDENTE,
    CONCLUIDO;

    public static Status from(String status){
        return Status.valueOf(status.toUpperCase());
    }
}
