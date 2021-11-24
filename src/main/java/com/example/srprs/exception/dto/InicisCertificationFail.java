package com.example.srprs.exception.dto;

import com.example.srprs.payment.dto.InicisMobileCertificationResponse;
import com.example.srprs.payment.dto.InicisWebCertificationResponse;
import lombok.Getter;

@Getter
public class InicisCertificationFail extends RuntimeException {
    private String message;

    public InicisCertificationFail(InicisWebCertificationResponse response) {
        super(response.getResultMessage());
        this.message = "이니시스 인증 실패(" + response.getResultCode() + "): " + response.getResultMessage();
    }

    public InicisCertificationFail(InicisMobileCertificationResponse response) {
        super(response.getP_RMESG1());
        this.message = "이니시스 인증 실패(" + response.getP_STATUS() + "): " + response.getP_RMESG1();
    }
}
