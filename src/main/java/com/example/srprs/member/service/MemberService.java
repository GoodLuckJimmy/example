package com.example.srprs.member.service;

import com.example.srprs.exception.dto.DuplicateUserException;
import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.domain.LoginProvider;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.domain.MemberRoleType;
import com.example.srprs.member.dto.ExternalLoginRequest;
import com.example.srprs.member.dto.MemberInfoChangeRequest;
import com.example.srprs.member.dto.SrprsLoginRequest;
import com.example.srprs.member.dto.MemberSignupRequest;
import com.example.srprs.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Value("${example.login.default-password}")
    private String defaultPassword;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(MemberSignupRequest request, LoginProvider provider) {
        Member member = MemberDtoToEntity(request, provider);
        try {
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException exception) {
           throw new DuplicateUserException();
        }
    }

    private Member MemberDtoToEntity(MemberSignupRequest request, LoginProvider provider) {
        return Member.builder()
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .role(MemberRoleType.CUSTOMER)
                .isFromSocial(!provider.equals(LoginProvider.BP))
                .provider(provider)
                .build();
    }

    @Transactional(readOnly = true)
    public Member findById(String id) {
        return memberRepository.findById(id) // todo: optonal로 변경
                .orElseThrow(() -> new LoginException("db에 없는 유저"));
    }


    public void validatePassword(final SrprsLoginRequest request, final Member member) {
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new LoginException("password match 오류");
        }
    }

    @Transactional
    public void updateMemberInfo(MemberInfoChangeRequest request) {
        Member member = findById(request.getId());
        member.changeInfo(request);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member createNewMember(ExternalLoginRequest request, LoginProvider provider) {
        MemberSignupRequest newMember = MemberSignupRequest.builder()
                .id(request.getUserKey())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .password(this.defaultPassword)
                .provider(provider)
                .build();

       return signUp(newMember, provider);

    }
}
