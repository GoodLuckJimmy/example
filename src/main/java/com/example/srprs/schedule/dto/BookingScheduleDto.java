package com.example.srprs.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookingScheduleDto {
    @Schema(title = "날짜", description = "yyyy-MM-dd", type = "string", example= "2020-12-12")
    private LocalDate date;

    @Schema(title = "도시명")
    private String cityName;

    @Schema(title = "경로")
    List<BookingSchedulePathDto> paths;

    @Schema(title = "첨부파일 url")
    private List<String> attachmentUrls;

    @Builder
    public BookingScheduleDto(LocalDate date, String cityName, List<BookingSchedulePathDto> paths, List<String> attachmentUrls) {
        this.date = date;
        this.cityName = cityName;
        this.paths = paths;
        this.attachmentUrls = attachmentUrls;
    }
}
