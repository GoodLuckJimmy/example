package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "get param")
public class BookingCancelFeeRequest {
    @Schema(title = "예약 번호")
    @NotNull
    private Long bookingNo;
}
