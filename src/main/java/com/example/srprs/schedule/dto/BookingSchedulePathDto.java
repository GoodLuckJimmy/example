package com.example.srprs.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class BookingSchedulePathDto {
    @Schema(title = "시간", description = "HH:mm:ss", type = "string", example= "13:00:00")
    private LocalTime time;

    @Schema(title = "제목")
    private String title;

    @Schema(title = "상세 일정")
    private String schedule;

    public BookingSchedulePathDto(LocalTime time, String title, String schedule) {
        this.time = time;
        this.title = title;
        this.schedule = schedule;
    }
}
