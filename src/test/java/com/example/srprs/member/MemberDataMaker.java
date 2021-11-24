package com.example.srprs.member;

import com.example.srprs.member.domain.Member;
import com.example.srprs.member.domain.MemberRoleType;
import com.example.srprs.member.dto.SrprsLoginRequest;
import com.example.srprs.member.dto.MemberSignupRequest;

public class MemberDataMaker {
    public static Member memberWithoutNo() {
        return Member.builder()
                .id("abc@b.com")
                .name("홍길동")
                .password("abc")
                .phoneNumber("010-131-1345")
                .role(MemberRoleType.CUSTOMER)
                .build();
    }

    public static Member memberWithNo() {
        return Member.builder()
                .no(123L)
                .id("abc@b.com")
                .name("홍길동")
                .password("abc")
                .phoneNumber("010-131-1345")
                .role(MemberRoleType.CUSTOMER)
                .build();
    }

    public static MemberSignupRequest memberRequest() {
        return MemberSignupRequest.builder()
                .id("abc@b.com")
                .name("홍길동")
                .password("abc")
                .phoneNumber("010-131-1345")
                .build();
    }

    public static SrprsLoginRequest loginRequest() {
        return new SrprsLoginRequest("abc@b.com", "abc");
    }
}
