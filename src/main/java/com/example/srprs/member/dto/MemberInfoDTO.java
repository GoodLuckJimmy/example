package com.example.srprs.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoDTO {
    private Long no;

    private String id;

    private String name;

    private String phoneNumber;

    @Builder
    public MemberInfoDTO(Long no, String id, String name, String phoneNumber) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
