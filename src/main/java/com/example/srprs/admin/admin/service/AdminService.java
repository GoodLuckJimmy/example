package com.example.srprs.admin.admin.service;

import com.example.srprs.admin.admin.domain.Admin;
import com.example.srprs.admin.admin.dto.AdminSignupRequest;
import com.example.srprs.admin.admin.repository.AdminRepository;
import com.example.srprs.exception.dto.DuplicateUserException;
import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.domain.MemberRoleType;
import com.example.srprs.member.dto.SrprsLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public void signIn(AdminSignupRequest request) {
        Admin admin = adminDtoToEntity(request);
        try {
            adminRepository.save(admin);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateUserException();
        }
    }

    private Admin adminDtoToEntity(AdminSignupRequest request) {
        return Admin.builder()
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(MemberRoleType.ADMIN)
                .build();
    }

    @Transactional
    public Admin findById(String id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new LoginException("db에 없는 유저"));

    }

    public void validatePassword(SrprsLoginRequest request, Admin admin) {
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new LoginException("password match 오류");
        }
    }
}
