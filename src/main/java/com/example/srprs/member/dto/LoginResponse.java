package com.example.srprs.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
