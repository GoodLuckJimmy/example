package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class NoBookingFoundException extends RuntimeException {
    private String message;

    public NoBookingFoundException(String message) {
        super(message);
        this.message = message;
    }
}
