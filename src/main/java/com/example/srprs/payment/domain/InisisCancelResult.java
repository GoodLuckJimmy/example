package com.example.srprs.payment.domain;

import com.example.srprs.common.repository.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_inisis_cancel_result_payment_no", columnList = "paymentNo DESC")
})
public class InisisCancelResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(length = 6)
    private String resultCode; // 결과코드["00":성공, 그외 실패]

    @Column(length = 100)
    private String resultMessage; // 결과메세지

    private LocalDate cancelDate; // 취소일자

    private LocalTime cancelTime; // 취소시간

    @Column(length = 9)
    private String cashCancelNumber; //	현금영수증 취소승인번호 * 현금영수증 발행건에 한함

    @Column(length = 6)
    private String detailResultCode; // 취소실패 응답시 상세코드

    @Column(length = 40)
    private String receiptInfo; // 특정 가맹점 전용 응답필드

    private Long paymentNo;
}
