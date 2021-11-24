package com.example.srprs.member.service;

import com.example.srprs.member.MemberDataMaker;
import com.example.srprs.member.domain.LoginProvider;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.dto.SrprsLoginRequest;
import com.example.srprs.member.dto.MemberSignupRequest;
import com.example.srprs.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    MemberService memberService;

    MemberRepository memberRepository = mock(MemberRepository.class);

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @BeforeEach
    void setup() {
        this.memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    void signUp() {
        // given
        when(memberRepository.save(any(Member.class)))
                .thenReturn(MemberDataMaker.memberWithoutNo());
        MemberSignupRequest request = MemberDataMaker.memberRequest();

        // when
        Member member = memberService.signUp(request, LoginProvider.BP);

        // then
        assertThat(member.getName()).isEqualTo("홍길동");

    }

    @Test
    void login() {
        // given
        when(memberRepository.findById(MemberDataMaker.loginRequest().getId()))
                .thenReturn(Optional.of(MemberDataMaker.memberWithoutNo()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        SrprsLoginRequest request = MemberDataMaker.loginRequest();

        // when
        memberService.findById(request.getId());

        // then
        Mockito.verify(memberRepository, times(1)).findById(request.getId());
    }
}