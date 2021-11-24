package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BookingScheduleRegisterRequest {
    @Schema(title = "예약 번호")
    @NotNull
    private Long bookingNo;

    @Schema(title = "일정")
    @NotEmpty
    private String schedule;

    @Builder
    public BookingScheduleRegisterRequest(Long bookingNo, String schedule) {
        this.bookingNo = bookingNo;
        this.schedule = schedule;
    }
}
