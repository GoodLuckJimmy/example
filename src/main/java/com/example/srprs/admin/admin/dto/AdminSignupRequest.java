package com.example.srprs.admin.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AdminSignupRequest {
    @Schema(title = "아이디")
    private String id;

    @Schema(title = "패스워드")
    private String password;

    @Builder
    public AdminSignupRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
