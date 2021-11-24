package com.example.srprs.config;

import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.MemberDataMaker;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JwtTokenProviderTest {
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup() {
        this.jwtTokenProvider = new JwtTokenProvider(1);
    }

    @Test
    void generateToken() {
        // given
        Member member = MemberDataMaker.memberWithNo();
        String token = jwtTokenProvider.generateToken(member);

        // when
        MemberDto memberDTO = jwtTokenProvider.extractMember(token);

        // then
        assertThat(memberDTO.getNo()).isEqualTo(member.getNo());
    }

    @Test
    void 토큰_유효기간_확인() throws InterruptedException {
        // given
        Member member = MemberDataMaker.memberWithNo();
        String token = jwtTokenProvider.generateToken(member);

        Thread.sleep(1100);

        // when
        Throwable throwable = catchThrowable(() -> {
            jwtTokenProvider.extractMember(token);
        });

        // then
        assertThat(throwable).isInstanceOf(LoginException.class);
    }

}