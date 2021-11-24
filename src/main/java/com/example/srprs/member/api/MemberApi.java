package com.example.srprs.member.api;

import com.example.srprs.config.JwtTokenProvider;
import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.domain.LoginProvider;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.dto.*;
import com.example.srprs.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name="회원", description = "회원 관련 api")
@Slf4j
public class MemberApi {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원 정보 가져오기", description = "회원정보를 가져온다. Authorization 헤더 필요")
    @GetMapping ("/members")
    public MemberInfoDTO member(@Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDTO ) {
        Member member = memberService.findById(memberDTO.getUsername());

        return MemberInfoDTO.builder()
                .no(member.getNo())
                .id(member.getId())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    @Operation(summary = "회원 가입", description = "사이트를 통한 회원가입")
    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void signIn(@Valid @RequestBody MemberSignupRequest request) {
        memberService.signUp(request, LoginProvider.BP);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정. Authorization 헤더 필요")
    @PutMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public void signIn(@RequestBody MemberInfoChangeRequest request) {
        memberService.updateMemberInfo(request);
    }

    @Operation(summary = "일반 로그인", description = "사이트를 통한 로그인, /members/login/bp로 변경")
    @PostMapping("/members/login")
    @Deprecated // todo: 앱적용후 삭제
    public LoginResponse srprsLoginOld(@RequestBody SrprsLoginRequest request) {
        Member member = memberService.findById(request.getId());
        memberService.validatePassword(request, member);

        return new LoginResponse(jwtTokenProvider.generateToken(member));
    }

    @Operation(summary = "일반 로그인", description = "사이트를 통한 로그인")
    @PostMapping("/members/login/bp")
    public LoginResponse srprsLogin(@RequestBody SrprsLoginRequest request) {
        Member member = memberService.findById(request.getId());
        memberService.validatePassword(request, member);

        return new LoginResponse(jwtTokenProvider.generateToken(member));
    }

    @Operation(summary = "카카오 로그인", description = "카카오를 통한 로그인, /members/klogin는 삭제 예정, /members/login/kakao으로 변경")
    @PostMapping("/members/klogin")
    @Deprecated // todo: 앱적용후 삭제
    public LoginResponse kakaoLoginOld(@Valid @RequestBody ExternalLoginRequest request) {
        Member member;
        try {
            member = findMember(request);
        } catch (LoginException exception) {
            log.debug("kakao 회원 등록");
            member = memberService.createNewMember(request, LoginProvider.KAKAO);
        }

        return new LoginResponse(jwtTokenProvider.generateToken(member));
    }

    @Operation(summary = "카카오 로그인", description = "카카오ID를 통한 로그인")
    @PostMapping("/members/login/kakao")
    public LoginResponse kakaoLogin(@Valid @RequestBody ExternalLoginRequest request) {
        Member member;
        try {
            member = findMember(request);
        } catch (LoginException exception) {
            log.debug("kakao 회원 등록");
            member = memberService.createNewMember(request, LoginProvider.KAKAO);
        }

        return new LoginResponse(jwtTokenProvider.generateToken(member));
    }

    @Operation(summary = "애플 로그인", description = "애플ID를 통한 로그인")
    @PostMapping("/members/login/apple")
    public LoginResponse appleLogin(@Valid @RequestBody ExternalLoginRequest request) {
        Member member;
        try {
            member = findMember(request);
        } catch (LoginException exception) {
            log.debug("애플 회원 등록");
            member = memberService.createNewMember(request, LoginProvider.APPLE);
        }

        return new LoginResponse(jwtTokenProvider.generateToken(member));
    }

    private Member findMember(@RequestBody @Valid ExternalLoginRequest request) {
        return memberService.findById(request.getUserKey());
    }

}
