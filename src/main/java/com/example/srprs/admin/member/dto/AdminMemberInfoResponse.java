package com.example.srprs.admin.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdminMemberInfoResponse {
    @Schema(title = "전체 회원 수")
    private int count;

    @Schema(title = "회원 정보")
    private List<AdminMemberDto> members;

    public AdminMemberInfoResponse(List<AdminMemberDto> memberDtos) {
        this.count = memberDtos.size();
        this.members = memberDtos;
    }
}
