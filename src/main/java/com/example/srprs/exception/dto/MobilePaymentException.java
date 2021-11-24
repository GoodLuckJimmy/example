package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class MobilePaymentException extends RuntimeException {
    private String message;
    private String status;

    public MobilePaymentException(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
