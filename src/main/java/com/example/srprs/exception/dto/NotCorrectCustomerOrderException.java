package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class NotCorrectCustomerOrderException extends RuntimeException {
    private String message;

    public NotCorrectCustomerOrderException(String message) {
        super(message);
        this.message = message;
    }
}
