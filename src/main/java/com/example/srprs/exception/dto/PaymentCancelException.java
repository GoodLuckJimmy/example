package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class PaymentCancelException extends RuntimeException {
    private String message;

    public PaymentCancelException(String message) {
        super(message);
        this.message = message;
    }
}
