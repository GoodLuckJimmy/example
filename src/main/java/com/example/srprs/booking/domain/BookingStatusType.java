package com.example.srprs.booking.domain;

public enum BookingStatusType {
    PRE_RESERVED, // 결제 전
    RESERVED, // 결제 후
    CANCELED, // 취소 됨
    REFUNDED, // 환불 됨

    ERROR_REFUNDED// 내부 오류로 인한 취소
}
