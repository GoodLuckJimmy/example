package com.example.srprs.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InicisWebCertificationResponse {
    @Schema(hidden = true, description = "결과코드 [0000: 정상, 이외 실패]")
    private String resultCode;

    @Schema(hidden = true, description = "결과메시지")
    private String resultMessage;

    @Schema(hidden = true, description = "상점아이디")
    private String mid;

    @Schema(hidden = true, description = "주문번호")
    private String orderNumber;

    @Schema(hidden = true, description = "승인요청 검증 토큰")
    private String authToken;

    @Schema(hidden = true, description = "승인요청 url")
    private String authUrl;

    @Schema(hidden = true, description = "망취소 요청 url")
    private String netCancelUrl;

    @Schema(hidden = true, description = "인증결과 인코딩[Default: UTF-8]")
    private String charset;

    @Schema(hidden = true, description = "가맹점 임의 데이터")
    private String merchantData;

    public boolean isCertificated() {
        return this.resultCode.equals("0000");
    }
}
