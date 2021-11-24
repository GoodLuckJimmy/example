package com.example.srprs.payment.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class InicisMobileCertificationResponse {
    private String P_STATUS; // 결과코드 ["00": 정상, 이외 실패]

    private String P_RMESG1; // 결과메시지

    private String P_TID; // 인증거래번호(성공시에만 전달)

    private BigDecimal P_AMT; // 거래금액

    private String P_REQ_URL; // 승인 요청 url

    private String P_NOTI; // 가맹점 임의 데이터


    public boolean isCertificated() {
        return "00".equals(this.P_STATUS);
    }
}
