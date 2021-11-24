package com.example.srprs.admin.admin.api;

import com.example.srprs.admin.admin.service.AdminService;
import com.example.srprs.admin.admin.domain.Admin;
import com.example.srprs.admin.admin.dto.AdminSignupRequest;
import com.example.srprs.config.JwtTokenProvider;
import com.example.srprs.member.dto.LoginResponse;
import com.example.srprs.member.dto.SrprsLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="어드민", description = "어드민 관련 api")
public class AdminApi {
    private final AdminService adminService;

    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원 가입", description = "어드민 회원가입")
    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public void signIn(@RequestBody AdminSignupRequest request) {
        adminService.signIn(request);
    }

    @Operation(summary = "로그인", description = "어드민 로그인")
    @PostMapping("/admins/login")
    public LoginResponse adminLogin(@RequestBody SrprsLoginRequest request) {
        Admin admin = adminService.findById(request.getId());
        adminService.validatePassword(request, admin);

        return new LoginResponse(jwtTokenProvider.generateToken(admin));
    }
}
