package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class BookingCancelException extends RuntimeException {
    private String message;

    public BookingCancelException(String message) {
        super(message);
        this.message = message;
    }
}
