package com.example.srprs.config;

import com.example.srprs.admin.admin.domain.Admin;
import com.example.srprs.exception.dto.LoginException;
import com.example.srprs.member.domain.Member;
import com.example.srprs.member.dto.MemberDto;
import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "bsrprsp";
    private long tokenValidationSec = 60 * 24 * 30; // 1 month

    public JwtTokenProvider(String secretKey) {
        this.secretKey = secretKey;
    }

    public JwtTokenProvider(long tokenValidationSec) {
        this.tokenValidationSec = tokenValidationSec;
    }

    public JwtTokenProvider(String secretKey, long tokenValidationSec) {
        this.secretKey = secretKey;
        this.tokenValidationSec = tokenValidationSec;
    }



    public UsernamePasswordAuthenticationToken getAuthentication(MemberDto memberDTO) {
        return new UsernamePasswordAuthenticationToken(memberDTO, null, memberDTO.getAuthorities());
    }

    public String generateToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("no", member.getNo());
        claims.put("name", member.getName());
        claims.put("phone", member.getPhoneNumber());
        claims.put("auth", member.getRole().toString());

        return compactJwt(claims);

    }

    public String generateToken(Admin admin) {
        Claims claims = Jwts.claims();
        claims.put("no", admin.getNo());
        claims.put("id", admin.getId());
        claims.put("auth", admin.getRole().toString());

        return compactJwt(claims);
    }

    private String compactJwt(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidationSec * 1000))
                .signWith(SignatureAlgorithm.HS512, getSecretKey())
                .compact();
    }

    public MemberDto extractMember(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();

            return new MemberDto(
                    Long.valueOf((Integer) claims.get("no")),
                    (String)claims.get("id"),
                    (String)claims.get("name"),
                    (String)claims.get("phone"),
                    "password",
                    List.of(new SimpleGrantedAuthority("ROLE_" + claims.get("auth"))));
        } catch (ExpiredJwtException | UnsupportedJwtException | SignatureException exception) {
            log.debug(exception.getLocalizedMessage());
            throw new LoginException("유효하지 않는 토큰입니다.");
        }
    }

    private byte[] getSecretKey() {
        return secretKey.getBytes(StandardCharsets.UTF_8);
    }


}
