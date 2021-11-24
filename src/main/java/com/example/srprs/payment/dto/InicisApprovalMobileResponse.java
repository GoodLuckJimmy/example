package com.example.srprs.payment.dto;

import com.example.srprs.payment.domain.InicisPayResult;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InicisApprovalMobileResponse {

    /* 공통 */
    @JsonProperty("P_STATUS")
    private String resultCode; // 결과코드 ["00":성공, 이외 실패 (실패코드 4byte)]

    @JsonProperty("P_RMESG1")
    private String resultMessage; // 결과메세지

    @JsonProperty("P_TID")
    private String tid; // 거래번호

    @JsonProperty("P_TYPE")
    private String payMethod; // 지불수단

    @JsonFormat(pattern = "yyyyMMddHHmmss")
    @JsonProperty("P_AUTH_DT")
    private LocalDateTime applyDate; // 승인일자

    @JsonProperty("P_MID")
    private String mid;

    @JsonProperty("P_OID")
    private String orderNo; // 주문번호

    @JsonProperty("P_AMT")
    private BigDecimal totalPrice; // 결제금액

    @JsonProperty("P_UNAME")
    private String buyerName; // 구매자명

    @JsonProperty("P_MNAME")
    private String mname; // 가맹점명

    @JsonProperty("P_NOTI")
    private String merchantData; // 가맹점 임의 데이터

    @JsonProperty("P_NOTEURL")
    private String merchantDataUrl; // 가맹점 전달 P_NOTI_URL

    @JsonProperty("P_NEXT_URL")
    private String merchantNextUrl; // 가맹점 전달 P_NEXT_URL

    /* 신용카드*/
    @JsonProperty("P_CARD_PRTC_CODE")
    private int partCancelAvailability; // 부분취소 가능여부 ["1":가능 , "0":불가능]

    @JsonProperty("CARD_CorpFlag")
    private String cardFlag; // 카드구분 ["0":개인카드, "1":법인카드, "9":구분불가]

    @JsonProperty("P_CARD_CHECKFLAG")
    private String cardKind; // 카드종류 ["0":신용카드, "1":체크카드, "2":기프트카드]

    @JsonProperty("P_RMESG2")
    private int cardQuota; // 할부기간

    @JsonProperty("P_CARD_NUM")
    private String cardNumber; // 신용카드번호

    @JsonProperty("P_AUTH_NO")
    private String authNumber; // 승인번호(신용카드 거래에만 사용)

    @JsonProperty("P_CARD_APPLPRICE")
    private BigDecimal cardPaidPrice; //	실제 카드승인 금액

    @JsonProperty("P_CARD_USEPOINT")
    private BigDecimal pointUsed = BigDecimal.ZERO; // 포인트 사용금액

    @JsonProperty("P_COUPON_DISCOUNT")
    private BigDecimal couponDiscount = BigDecimal.ZERO; //	쿠폰(즉시할인) 금액

    public InicisPayResult toEntity() {
        InicisPayResult entity = new InicisPayResult();
        entity.setResultCode(this.resultCode);
        entity.setResultMessage(this.resultMessage);
        entity.setTid(this.tid);
        entity.setGoodName(null);
        entity.setTotalPrice(totalPrice);
        entity.setOrderNo(this.orderNo);
        entity.setPayMethod(this.payMethod);
        entity.setApplyNumber(null);
        entity.setApplyDate(this.applyDate.toLocalDate());
        entity.setApplyTime(this.applyDate.toLocalTime());
        entity.setEventCode(null);
        entity.setBuyerName(this.buyerName);
        entity.setBuyerTel(null);
        entity.setCustomerEmail(null);
        entity.setCardNumber(this.cardNumber);
        entity.setCardQuota(this.cardQuota);
        entity.setCardFlag(this.cardFlag);
        entity.setCardKind(this.cardKind);
        entity.setPartCancelAvailability(this.partCancelAvailability == 1);
        entity.setCardPointUsed(this.pointUsed.compareTo(BigDecimal.ZERO) > 0);
        entity.setCardPaidPrice(this.cardPaidPrice);
        entity.setCouponDiscount(this.couponDiscount);
        entity.setPointUsed(this.pointUsed);

        return entity;
    }
}
