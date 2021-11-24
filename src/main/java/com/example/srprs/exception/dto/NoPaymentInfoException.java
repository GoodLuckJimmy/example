package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class NoPaymentInfoException extends RuntimeException {
    private String message;

    public NoPaymentInfoException(String message) {
        this.message = message;
    }
}
