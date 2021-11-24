package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class NotPaidException extends RuntimeException {
    private String message;

    public NotPaidException(String message) {
        super(message);
        this.message = message;
    }
}
