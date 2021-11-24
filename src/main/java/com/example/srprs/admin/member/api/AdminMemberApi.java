package com.example.srprs.admin.member.api;

import com.example.srprs.admin.member.dto.AdminMemberDto;
import com.example.srprs.admin.member.dto.AdminMemberInfoResponse;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="어드민")
public class AdminMemberApi {
    private final MemberService memberService;

    @Operation(summary = "전체 회원 조회", description = "일반 회원정보 가져오기")
    @GetMapping("/admins/members")
    public AdminMemberInfoResponse signIn() {
        List<Member> members = memberService.findAll();

        List<AdminMemberDto> memberDtos = members.stream().map(AdminMemberDto::new)
                .collect(Collectors.toList());

        return new AdminMemberInfoResponse(memberDtos);
    }
}
