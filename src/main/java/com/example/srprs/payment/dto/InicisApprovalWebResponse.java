package com.example.srprs.payment.dto;

import com.example.srprs.payment.domain.InicisPayResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
public class InicisApprovalWebResponse {
    /* 공통 */
    private String resultCode; // 결과코드 ["0000":성공, 이외 실패 (실패코드 6byte)]

    @JsonProperty("resultMsg")
    private String resultMessage; // 결과메세지

    private String tid; // 거래번호

    private String goodName; // 상품명

    @JsonProperty("TotPrice")
    private BigDecimal totalPrice; // 결제금액

    @JsonProperty("MOID")
    private String orderNo; // 주문번호

    private String payMethod; // 지불수단

    @JsonProperty("applNum")
    private String applyNumber; // 승인번호

    @JsonFormat(pattern = "yyyyMMdd")
    @JsonProperty("applDate")
    private LocalDate applyDate; // 승인일자

    @JsonFormat(pattern = "HHmmss")
    @JsonProperty("applTime")
    private LocalTime applyTime; // 승인시간

    @JsonProperty("EventCode")
    private String eventCode; // 이벤트 코드

    private String buyerName; // 구매자명

    private String buyerTel; // 구매자 휴대폰번호

    private String buyerEmail; // 구매자 이메일

    @JsonProperty("custEmail")
    private String customerEmail; // 최종 이메일

    /* 신용카드*/
    @JsonProperty("CARD_Num")
    private String cardNumber; // 신용카드번호

    @JsonProperty("CARD_Quota")
    private int cardQuota; // 할부기간

    @JsonProperty("CARD_CorpFlag")
    private String cardFlag; // 카드구분 ["0":개인카드, "1":법인카드, "9":구분불가]

    @JsonProperty("CARD_CheckFlag")
    private String cardKind; // 카드종류 ["0":신용카드, "1":체크카드, "2":기프트카드]

    @JsonProperty("CARD_PRTC_CODE")
    private String partCancelAvailability; // 부분취소 가능여부 ["1":가능 , "0":불가능]

    @JsonProperty("CARD_Point")
    private String cardPointUsed; // 카드 포인트 사용 여부["": 사용안함 "1": 사용]

    @JsonProperty("CARD_CouponPrice")
    private BigDecimal cardPaidPrice; //	실제 카드승인 금액

    @JsonProperty("CARD_CouponDiscount")
    private BigDecimal couponDiscount; //	쿠폰(즉시할인) 금액

    @JsonProperty("CARD_UsePoint")
    private BigDecimal pointUsed; // 포인트 사용금액

    public InicisPayResult toEntity() {
        return InicisPayResult.builder()
                .resultCode(this.resultCode)
                .resultMessage(this.resultMessage)
                .tid(this.tid)
                .goodName(this.goodName)
                .totalPrice(this.totalPrice)
                .orderNo(this.orderNo)
                .payMethod(this.payMethod)
                .applyNumber(this.applyNumber)
                .applyDate(this.applyDate)
                .applyTime(this.applyTime)
                .eventCode(this.eventCode)
                .buyerName(this.buyerName)
                .buyerTel(this.buyerTel)
                .buyerEmail(this.buyerEmail)
                .cardNumber(this.cardNumber)
                .cardQuota(this.cardQuota)
                .cardKind(this.cardKind)
                .partCancelAvailability(this.partCancelAvailability.equals("1"))
                .cardPointUsed(this.cardPointUsed.equals("1"))
                .cardPaidPrice(this.cardPaidPrice)
                .couponDiscount(this.couponDiscount)
                .pointUsed(this.pointUsed)
                .build();
    }
}
