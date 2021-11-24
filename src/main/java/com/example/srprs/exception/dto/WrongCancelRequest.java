package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class WrongCancelRequest extends RuntimeException {
    private String message;

    public WrongCancelRequest(String message) {
        this.message = message;
    }
}
