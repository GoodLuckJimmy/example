package com.example.srprs.payment.dto;

import com.example.srprs.payment.domain.InisisCancelResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class InisisCancelResponse {
    private String resultCode; // 결과코드["00":성공, 그외 실패]

    @JsonProperty("resultMsg")
    private String resultMessage; // 결과메세지

    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate cancelDate; // 취소일자 [YYYYMMDD]	8

    @JsonFormat(pattern = "HHmmss")
    private LocalTime cancelTime; // 취소시간 [hhmmss]	6

    @JsonProperty("cshrCancelNum")
    private String cashCancelNumber; //	현금영수증 취소승인번호 * 현금영수증 발행건에 한함	9

    private String detailResultCode; // 취소실패 응답시 상세코드	6

    private String receiptInfo; // 특정 가맹점 전용 응답필드 40

    public InisisCancelResult toEntity(Long paymentNo) {
        InisisCancelResult inisisCancelResult = new InisisCancelResult();
        inisisCancelResult.setResultCode(this.resultCode);
        inisisCancelResult.setResultMessage(this.resultMessage);
        inisisCancelResult.setCancelDate(this.cancelDate);
        inisisCancelResult.setCancelTime(this.cancelTime);
        inisisCancelResult.setCashCancelNumber(this.cashCancelNumber);
        inisisCancelResult.setDetailResultCode(this.detailResultCode);
        inisisCancelResult.setReceiptInfo(this.receiptInfo);
        inisisCancelResult.setPaymentNo(paymentNo);

        return inisisCancelResult;
    }
}
