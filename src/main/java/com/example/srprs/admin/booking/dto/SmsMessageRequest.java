package com.example.srprs.admin.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SmsMessageRequest {
    @Schema(title = "예약번호")
    private Long bookingNo;

    @Schema(title = "내용")
    private String message;
}
