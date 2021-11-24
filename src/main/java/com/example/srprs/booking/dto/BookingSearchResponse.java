package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookingSearchResponse {
    @Schema(title = "예약 리스트", description = "지금까지 예약했던 내역 리스트")
    private List<BookingHistoryDto> bookingHistories;

    public BookingSearchResponse(List<BookingHistoryDto> bookingHistoryDtos) {
        this.bookingHistories = bookingHistoryDtos;
    }
}
