package com.example.srprs.admin.schedule.dto;

import com.example.srprs.schedule.dto.BookingSchedulePathDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookingScheduleRequest {
    @Schema(title = "도시명")
    private String cityName;

    @Schema(title = "날짜", description = "yyyy-MM-dd", type = "string", example= "2020-12-12")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Schema(title = "경로")
    List<BookingSchedulePathDto> paths;

    @Builder
    public BookingScheduleRequest(String cityName, LocalDate date, List<BookingSchedulePathDto> paths) {
        this.cityName = cityName;
        this.date = date;
        this.paths = paths;
    }
}
