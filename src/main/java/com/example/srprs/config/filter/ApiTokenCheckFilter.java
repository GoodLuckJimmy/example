package com.example.srprs.config.filter;

import com.example.srprs.config.JwtTokenProvider;
import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ApiTokenCheckFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (hasToken(request)) {

            try {
                String token = resolveToken(request);
                MemberDto memberDTO = jwtTokenProvider.extractMember(token);
                UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(memberDTO);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (LoginException exception) {
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        return authHeader.substring(7);
    }

    public boolean hasToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            try {
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return false;
    }
}
