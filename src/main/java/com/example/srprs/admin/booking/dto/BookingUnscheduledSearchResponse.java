package com.example.srprs.admin.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookingUnscheduledSearchResponse {
    @Schema(title = "예약 리스트", description = "예약 리스트 중 일정되지 않은 내역만 가져온다.")
    private List<UnscheduledBookingDto> unscheduledBookingDtos;

    public BookingUnscheduledSearchResponse(List<UnscheduledBookingDto> unscheduledBookingDtos) {
        this.unscheduledBookingDtos = unscheduledBookingDtos;
    }
}
