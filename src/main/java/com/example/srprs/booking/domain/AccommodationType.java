package com.example.srprs.booking.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "숙소 종류", description = "게스트 하우스, 호텔, 리조트, 프리미엄 호텔")
public enum AccommodationType {
    GUEST_HOUSE,
    HOTEL,
    RESORT,
    PREMIUM_HOTEL
}
