package com.example.srprs.booking.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Embeddable
@Setter
@Getter
public class BookingStatus {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BookingStatusType bookingStatusType;

    private boolean isCancel;

    private boolean isRefund;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundDate;

    public BookingStatus() {
        this.bookingStatusType = BookingStatusType.PRE_RESERVED;
        this.isCancel = false;
        this.isRefund = false;
    }
}
