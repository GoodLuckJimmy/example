package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "추가 서비스 종류", description = "추가 구매 가능한 서비스. 반려동물 동반, 서울근방, 조식, 지방 출발, 반나절 투어")
public enum RequestOptionType {
    PET, // 반려동물 동반
    SEOUL, // 서울 경기
    BREAKFAST, // 조식
    PROVINCES, // 지방 출발
    HALF_DAY // 반나절 투어
}
