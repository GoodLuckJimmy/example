package com.example.srprs.member.service;

import com.example.srprs.member.domain.Member;
import com.example.srprs.member.dto.MemberDto;
import com.example.srprs.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자입니다."));

        return new MemberDto(
                member.getNo(),
                member.getId(),
                member.getName(),
                member.getPhoneNumber(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole())));
    }
}
