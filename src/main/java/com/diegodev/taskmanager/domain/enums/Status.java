package com.diegodev.taskmanager.domain.enums;

public enum Status {
    PENDING,
    DONE;

    public static Status from(String status){
        return Status.valueOf(status.toUpperCase());
    }
}
