package com.example.srprs.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ExternalLoginRequest {

    @Schema(title = "unique key", description = "이메일")
    @NotEmpty
    private String userKey;

    @Schema(title = "이름", description = "사용자 이름 optional")
    private String name;

    @Schema(title = "전화번호", description = "전화번호 optional")
    private String phoneNumber;

    public ExternalLoginRequest(String userKey) {
        this.userKey = userKey;
    }

    public String getName() {
        return Optional.ofNullable(this.name)
                .orElse("");
    }

    public String getPhoneNumber() {
        return Optional.ofNullable(this.phoneNumber)
                .orElse("");
    }
}
