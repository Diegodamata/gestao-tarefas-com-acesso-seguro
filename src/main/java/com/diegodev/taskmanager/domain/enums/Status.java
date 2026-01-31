package com.diegodev.taskmanager.domain.enums;

public enum Status {
    PENDENTE,
    FEITO;

    public static Status from(String status){
        return Status.valueOf(status.toUpperCase());
    }
}
