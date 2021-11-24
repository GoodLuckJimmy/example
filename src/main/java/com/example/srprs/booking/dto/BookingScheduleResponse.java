package com.example.srprs.booking.dto;

import com.example.srprs.schedule.dto.BookingScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class BookingScheduleResponse {
    @Schema(title = "예약번호")
    private Long bookingNo;

    @Schema(title = "일정")
    private List<BookingScheduleDto> schedules;

    public BookingScheduleResponse(Long bookingNo, List<BookingScheduleDto> schedules) {
        this.bookingNo = bookingNo;
        this.schedules = schedules;
    }
}
