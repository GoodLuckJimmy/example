package com.example.srprs.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BookingPaymentRequest {
    @Schema(title = "예약 번호")
    @NotNull
    private Long bookingNo;

    @Schema(title = "결제 번호", description = "결제시 생성된 결제 번호")
    @NotBlank
    @Deprecated
    private String tid;

    @Schema(title = "최종 결제 금액", description = "고객이 최종 결제한 금액")
    @NotNull @Positive
    @Deprecated
    private BigDecimal paidAmount;

    public BookingPaymentRequest(Long bookingNo, String tid, BigDecimal paidAmount) {
        this.bookingNo = bookingNo;
        this.tid = tid;
        this.paidAmount = paidAmount;
    }
}
