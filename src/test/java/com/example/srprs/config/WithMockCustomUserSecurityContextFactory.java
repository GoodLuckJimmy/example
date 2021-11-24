package com.example.srprs.config;

import com.example.srprs.member.domain.WithMockMember;
import com.example.srprs.member.dto.MemberDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        MemberDto dto = new MemberDto(
                annotation.no(),
                annotation.id(),
                annotation.name(),
                annotation.phoneNumber(),
                annotation.password(),
                List.of(new SimpleGrantedAuthority(annotation.roles())));

        context.setAuthentication(new UsernamePasswordAuthenticationToken(dto,dto.getPassword() , dto.getAuthorities()));
        return context;
    }
}
