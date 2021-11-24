package com.example.srprs.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Setter
@Getter
public class MemberDto extends User {
    private Long no;

    private String id;

    private String name;

    private String phoneNumber;

    public MemberDto(Long no,
                     String id,
                     String name,
                     String phoneNumber,
                     String password,
                     Collection<? extends GrantedAuthority> authorities) {
        super(id, password, authorities);
        this.no = no;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
