package com.example.srprs.exception.dto;

import lombok.Getter;

@Getter
public class ApiExceptionResponse {
    private String message;

    public ApiExceptionResponse(String message) {
        this.message = message;
    }
}
