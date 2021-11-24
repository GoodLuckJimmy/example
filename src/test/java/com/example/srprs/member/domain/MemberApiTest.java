package com.example.srprs.member.domain;

import com.example.srprs.common.MockMvcSetting;
import com.example.srprs.config.JwtTokenProvider;
import com.example.srprs.member.MemberDataMaker;
import com.example.srprs.member.api.MemberApi;
import com.example.srprs.member.dto.LoginResponse;
import com.example.srprs.member.dto.MemberSignupRequest;
import com.example.srprs.member.dto.SrprsLoginRequest;
import com.example.srprs.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberApi.class)
class MemberApiTest extends MockMvcSetting {
    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void signIn() throws Exception {
        // given
        MemberSignupRequest request = MemberDataMaker.memberRequest();

        // then
        this.mvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void login() throws Exception {
        // given
        SrprsLoginRequest request = MemberDataMaker.loginRequest();
        when(memberService.findById(anyString())).thenReturn(MemberDataMaker.memberWithoutNo());
        when(jwtTokenProvider.generateToken(any(Member.class))).thenReturn("131asfjakf");

        // when
        String body = this.mvc.perform(post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        LoginResponse loginResponse = objectMapper.readValue(body, LoginResponse.class);
        assertThat(loginResponse.getToken()).isNotEmpty();
    }

}