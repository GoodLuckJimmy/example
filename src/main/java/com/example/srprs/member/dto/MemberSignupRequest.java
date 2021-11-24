package com.example.srprs.member.dto;

import com.example.srprs.member.domain.LoginProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignupRequest {
    @Schema(title = "회원 아이디")
    @NotEmpty
    private String id;

    @Schema(title = "패스워드")
    @NotEmpty
    private String password;

    @Schema(title = "회원 이름")
    @NotEmpty
    private String name;

    @Schema(title = "전화번호")
    @NotEmpty
    private String phoneNumber;

    @Schema(hidden = true)
    private LoginProvider provider;

    @Builder
    public MemberSignupRequest(String id, String password, String name, String phoneNumber, LoginProvider provider) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
    }
}
