package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponse {
    @Schema(title = "예약번호", description = "예약 생성시 생성되는 번호")
    private Long bookingNo;

    public BookingResponse(Long bookingNo) {
        this.bookingNo = bookingNo;
    }
}
