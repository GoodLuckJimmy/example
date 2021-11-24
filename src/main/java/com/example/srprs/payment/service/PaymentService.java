package com.example.srprs.payment.service;

import com.example.srprs.payment.domain.InicisPayResult;
import com.example.srprs.payment.domain.InisisCancelResult;
import com.example.srprs.payment.dto.InisisCancelResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentService {
    private final InicisService inicisService;

    @Transactional
    public void refund(Long paymentNo) {
        InicisPayResult inicisPayResult = inicisService.find(paymentNo);

        log.debug("결제 취소 요청(payment no: {})...", paymentNo);
        InisisCancelResponse inisisCancelResponse = inicisService.cancelRequest(inicisPayResult.getTid());

        if (!"00".equals(inisisCancelResponse.getResultCode())){
            log.error("결제 취소 오류:" + inisisCancelResponse.getResultCode() + ": " + inisisCancelResponse.getResultMessage());
        } else {
            log.debug("결제 취소 성공(payment no: {})", paymentNo);
        }

        InisisCancelResult inisisCancelResult = inisisCancelResponse.toEntity(paymentNo);
        inicisService.saveCancelResult(inisisCancelResult);
    }

}
