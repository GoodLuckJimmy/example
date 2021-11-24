package com.example.srprs.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoChangeRequest {
    @Schema(title = "회원 번호")
    @NotNull
    private Long no;

    @Schema(title = "변경될 회원 아이디")
    private String id;

    @Schema(title = "변경될 회원 이름")
    private String name;

    @Schema(title = "변경될 회원 전화번호")
    private String phoneNumber;

    public MemberInfoChangeRequest(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

