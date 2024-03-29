package com.example.srprs.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SrprsLoginRequest {
    @Schema(title = "아이디")
    private String id;

    @Schema(title = "패스워드")
    private String password;

    public SrprsLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
