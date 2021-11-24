package com.example.srprs.admin.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleRequest {
    @Schema(title = "예약 번호")
    private Long bookingNo;

    @Schema(title = "전체 일정")
    private List<BookingScheduleRequest> schedules;

    public ScheduleRequest(Long bookingNo, List<BookingScheduleRequest> schedules) {
        this.bookingNo = bookingNo;
        this.schedules = schedules;
    }
}
