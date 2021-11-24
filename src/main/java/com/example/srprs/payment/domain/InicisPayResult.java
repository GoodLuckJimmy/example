package com.example.srprs.payment.domain;

import com.example.srprs.common.repository.BaseEntity;
import com.example.srprs.util.StringAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_inicis_pay_result_order_no", columnList = "orderNo DESC")
})
@ToString
public class InicisPayResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 6)
    private String resultCode; // 결과코드 ["0000":성공, 이외 실패 (실패코드 6byte)]

    @Column(length = 100)
    private String resultMessage; // 결과메세지

    @Column(length = 40)
    private String tid; // 거래번호

    @Column(length = 40)
    private String goodName; // 상품명

    private BigDecimal totalPrice; // 총 결제금액

    @Column(length = 40)
    private String orderNo; // 주문번호(=예약번호)

    @Column(length = 12)
    private String payMethod; // 지불수단

    @Column(length = 8)
    private String applyNumber; // 승인번호

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applyDate; // 승인일자

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime applyTime; // 승인시간

    @Column(length = 2)
    private String eventCode; // 이벤트 코드

    @Convert(converter = StringAttributeConverter.class)
    private String buyerName; // 구매자명

    @Convert(converter = StringAttributeConverter.class)
    private String buyerTel; // 구매자 휴대폰번호

    @Convert(converter = StringAttributeConverter.class)
    private String buyerEmail; // 구매자 이메일

    @Convert(converter = StringAttributeConverter.class)
    private String customerEmail; // 최종 이메일

    /* 신용카드*/
    @Column(length = 16)
    private String cardNumber; // 신용카드번호

    @Column(length = 2)
    private int cardQuota; // 할부기간

    @Column(length = 1)
    private String cardFlag; // 카드구분 ["0":개인카드, "1":법인카드, "9":구분불가]

    @Column(length = 1)
    private String cardKind; // 카드종류 ["0":신용카드, "1":체크카드, "2":기프트카드]

    @Column(length = 1)
    private boolean partCancelAvailability; // 부분취소 가능여부 ["1":가능 , "0":불가능]

    @Column(length = 1)
    private boolean cardPointUsed; // 카드 포인트 사용 여부["": 사용안함 "1": 사용]

    private BigDecimal cardPaidPrice; //	실제 카드승인 금액

    private BigDecimal couponDiscount; //	쿠폰(즉시할인) 금액

    private BigDecimal pointUsed; // 포인트 사용금액

    public boolean isSuccess() {
        return "0000".equals(resultCode) || "00".equals(resultCode);
    }
}
