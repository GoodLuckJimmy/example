package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BookingCancelRequest {
    @Schema(title = "예약 번호", description = "예약 번호")
    @NotNull
    private Long bookingNo;

    @Schema(title = "취소 수수료", description = "취소 수수료. 예약 취소 수수료 조회 api 참조")
    @PositiveOrZero
    private BigDecimal cancelFee;

    public BigDecimal getCancelFee() {
        if (cancelFee == null) {
            return BigDecimal.ZERO;
        }
        return cancelFee;
    }
}
