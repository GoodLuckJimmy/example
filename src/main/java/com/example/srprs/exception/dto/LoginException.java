package com.example.srprs.exception.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginException extends RuntimeException {
    private String message;

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
        this.message = message;
    }
}
