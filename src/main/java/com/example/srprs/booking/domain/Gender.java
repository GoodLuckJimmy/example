package com.example.srprs.booking.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "성별", description = "예약자 성별. 남, 여, 해당없음")
public enum Gender {
    MALE,
    FEMALE,
    NON_BINARY
}
