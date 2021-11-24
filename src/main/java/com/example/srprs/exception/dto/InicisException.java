package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class InicisException extends RuntimeException {
    private String message;

    public InicisException(String message) {
        super(message);
        this.message = message;
    }
}
