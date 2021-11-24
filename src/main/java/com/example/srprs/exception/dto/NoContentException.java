package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class NoContentException extends RuntimeException {
    private String message;

    public NoContentException(String message) {
        super(message);
        this.message = message;
    }
}
