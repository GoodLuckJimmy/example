package com.example.srprs.booking.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BookingCancelFeeResponse {
    private Long bookingNo;
    private BigDecimal feel;

    public BookingCancelFeeResponse(Long bookingNo, BigDecimal feel) {
        this.bookingNo = bookingNo;
        this.feel = feel;
    }
}
