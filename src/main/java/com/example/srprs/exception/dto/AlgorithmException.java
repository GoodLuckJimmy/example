package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class AlgorithmException extends RuntimeException {
    private String message;

    public AlgorithmException(String message) {
        super(message);
        this.message = message;
    }
}
