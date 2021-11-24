package com.example.srprs.admin.member.dto;

import com.example.srprs.member.domain.Member;
import com.example.srprs.member.domain.MemberRoleType;
import com.example.srprs.util.StringAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AdminMemberDto {
    @Schema(title = "회원 번호")
    private Long no;

    @Schema(title = "가입일")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @Schema(title = "아이디")
    private String id;

    @Schema(title = "이름")
    private String name;

    @Schema(title = "전화번호")
    private String phoneNumber;

    @Schema(title = "소셜아이디로 가입 유무")
    private boolean isFromSocial;

    public AdminMemberDto(Member member) {
        this.no = member.getNo();
        this.regDate = member.getRegDate();
        this.id = member.getId();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.isFromSocial = member.isFromSocial();
    }
}
