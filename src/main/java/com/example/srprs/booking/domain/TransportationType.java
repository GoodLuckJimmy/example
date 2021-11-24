package com.example.srprs.booking.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "교통 수단 종류", description = "대중교통, 자가, 타고가요")
public enum TransportationType {
    MY_CAR,
    PUBLIC_TRANSIT,
    TAGOGAYO
}
