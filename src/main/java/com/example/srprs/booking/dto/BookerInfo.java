package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class BookerInfo {
    private Gender gender;

    @Schema(title = "이메일", description = "예약자 이메일")
    @Email
    private String email;

    @Schema(title = "전화번호", description = "예약자 전화번호")
    @Pattern(regexp="(01[016789])(\\d{3,4})(\\d{4})", message="올바른 전화번호를 입력해주세요")
    @NotEmpty
    private String phoneNumber;

    @Schema(title = "이름", description = "예약자 이름")
    @NotEmpty
    private String name;

    public BookerInfo(Gender gender, String email, String phoneNumber, String name) {
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
