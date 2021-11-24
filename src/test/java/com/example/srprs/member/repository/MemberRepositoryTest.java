package com.example.srprs.member.repository;

import com.example.srprs.common.JPATestSetting;
import com.example.srprs.member.MemberDataMaker;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.domain.MemberRoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends JPATestSetting {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void findById() {
        // given
        Member member = MemberDataMaker.memberWithoutNo();
        memberRepository.save(member);

        // when
        Optional<Member> foundMember = memberRepository.findById(member.getId());

        // then
        Member savedMember = foundMember.get();
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isEqualTo(member.getId());
        assertThat(savedMember.getName()).isEqualTo(member.getName());

    }

    @Test
    void save() {
        // given
        Member member = MemberDataMaker.memberWithoutNo();
        String purePassword = member.getPassword();
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // when
        Member savedMember = memberRepository.save(member);


        // then
        assertThat(savedMember.getNo()).isPositive();
        assertThat(savedMember.getId()).isEqualTo(member.getId());
        assertThat(passwordEncoder.matches(purePassword, savedMember.getPassword())).isTrue();
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(savedMember.getRole()).isEqualTo(MemberRoleType.CUSTOMER);
    }
}