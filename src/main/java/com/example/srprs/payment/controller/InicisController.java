package com.example.srprs.payment.controller;

import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.service.BookingService;
import com.example.srprs.common.service.SlackService;
import com.example.srprs.exception.dto.InicisCertificationFail;
import com.example.srprs.exception.dto.MobilePaymentException;
import com.example.srprs.exception.dto.PaymentCancelException;
import com.example.srprs.message.service.SmsSender;
import com.example.srprs.payment.domain.InicisPayResult;
import com.example.srprs.payment.dto.*;
import com.example.srprs.payment.service.InicisService;
import com.example.srprs.util.EuckrEncoder;
import com.inicis.std.util.SignatureUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@Slf4j
@Tag(name = "결제", description = "pc결제 승인요청 응답 url: /payments/inicis/certification/web\t\n" +
        "모바일 결제 승인 요청 응답 url: /payments/inicis/certification/mobile")
@RequiredArgsConstructor
public class InicisController {
    private final InicisService inicisService;

    private final BookingService bookingService;

    private final SlackService slackService;

    private final SmsSender smsSender;

    @Value("${inicis.mid}")
    private String mid;

    @Schema(hidden = true, title = "이니시스 테스트용 PC결제페이지")
    @GetMapping("/pg/web_payment")
    public void webPayment(Model model, @RequestParam String bookingNo) throws Exception {
        log.debug("web결제 test page 호출 bookingNo: {}", bookingNo);

        String timeStamp = SignatureUtil.getTimestamp();
        String email = "bp@example.com";
        BigDecimal price = BigDecimal.valueOf(1000);

        model.addAttribute("mid", this.mid);
        model.addAttribute("mKey", inicisService.getMKey());
        model.addAttribute("signature", inicisService.getSignature(bookingNo, timeStamp, price));
        model.addAttribute("oid", bookingNo);
        model.addAttribute("timestamp", timeStamp);
        model.addAttribute("email", email);
        model.addAttribute("price",price.toPlainString());
        model.addAttribute("redirectUrl","localhost:5000/pg/web_payment?bookingNo=");
    }

    @Schema(hidden = true, title = "이니시스 테스트용 모바일 결제페이지")
    @GetMapping("/pg/mobile_payment")
    public void mobilePayment(Model model, @RequestParam String bookingNo) throws Exception {
        log.debug("모바일 결제 test page 호출: bookingNo:{}", bookingNo);

        BigDecimal price = BigDecimal.valueOf(1000);

        model.addAttribute("mid", this.mid);
        model.addAttribute("oid", bookingNo);
        model.addAttribute("price",price.toPlainString());
        model.addAttribute("redirectUrl","localhost:5000/pg/mobile_payment?bookingNo=");
    }

    @PostMapping("/payments/inicis/certification/web")
    @Transactional(noRollbackFor = PaymentCancelException.class)
    @Schema(title = "PC 결제 인증결과 수신", description = "PC용 이니시스 step2 인증결과수신 응답 받는 이니시스 callback용")
    public RedirectView inicisWebCertification(InicisWebCertificationResponse response) {
        if (response.isCertificated()) {
            log.debug("이니시스 웹 인증결과 정상 수신, 승인 요청 시작");
            InicisApprovalWebResponse approvalResponse = inicisService.approvalWeb(response);
            log.debug("이니시스 승인 요청 완료");

            InicisPayResult inicisPayResult = saveInicisApprovalResult(approvalResponse);

            log.info("{}번 예약 결제 결과: {}: {}",
                    inicisPayResult.getOrderNo(),
                    inicisPayResult.getResultCode(),
                    inicisPayResult.getResultMessage());

            Booking booking = null;
            try {
                if (inicisPayResult.isSuccess()) {
                    booking = savePaymentInfo(inicisPayResult);
                    log.debug("이니스스 결제결과 저장(booking no: {})", booking.getNo());
                }
            } catch (Exception exception) {
                log.error("이니시스 결제결과 저장 중 오류(bookingNo: {})", booking.getNo(), exception);

                log.debug("이니시스 결제 취소 요청...");
                cancelInicisPayment(inicisPayResult, booking);
                log.debug("이니시스 결제 취소 완료");

                String redirectUrl = response.getMerchantData() + "?resultCode=500&orderNumber=" + booking.getNo();
                return new RedirectView(redirectUrl);
            }

            sendSmsMessage(Optional.ofNullable(booking));

            sendSlackAlarm(booking.getNo());

            String redirectUrl = response.getMerchantData() + "?resultCode="+inicisPayResult.getResultCode() + "&orderNumber=" + inicisPayResult.getOrderNo();
            log.debug("이니시스 PC 결과 프론트 전송: {}({}) {}", inicisPayResult.getResultCode(), inicisPayResult.getResultMessage(), redirectUrl);

            return new RedirectView(redirectUrl);
        }

        throw new InicisCertificationFail(response);
    }

    private InicisPayResult saveInicisApprovalResult(InicisApprovalWebResponse approvalResponse) {
        return inicisService.saveApprovalInfo(approvalResponse.toEntity());
    }

    private void sendSmsMessage(Optional<Booking> booking) {
        booking.ifPresent(b -> {
            try {
                smsSender.sendPaidMessage(b.getPhoneNumber());
            }catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        });
    }

    private void cancelInicisPayment(InicisPayResult inicisPayResult, Booking booking) {
        InisisCancelResponse inisisCancelResponse = inicisService.cancelRequest(inicisPayResult.getTid());
        inicisService.saveCancelResult(inisisCancelResponse.toEntity(booking.getPaymentNo()));
    }

    private Booking savePaymentInfo(InicisPayResult inicisPayResult) {
        return bookingService.paid(
                Long.valueOf(inicisPayResult.getOrderNo()),
                inicisPayResult.getNo(),
                inicisPayResult.getTotalPrice());
    }


    @PostMapping("/payments/inicis/certification/mobile")
    @Transactional
    @Schema(title = "모바일 결제 인증결과 수신", description = "모바일용 이니시스 step2 인증결과수신 응답 받는 이니시스 callback용")
    public RedirectView inicisMobileCertification(InicisMobileCertificationResponse response) {
        log.debug("이니시스 모바일 인증결과 수신");

        if (!response.isCertificated()) {
            log.warn("이니시스 오류: {}({})", response.getP_STATUS(), EuckrEncoder.toUtf(response.getP_RMESG1()));
            throw new InicisCertificationFail(response);
        }

        InicisApprovalMobileResponse approvalResponse;

        try {
            log.debug("이니시스 모바일 인증결과 정상 수신");
            approvalResponse = requestInicisApproval(response);
        } catch (MobilePaymentException exception) {
            return new RedirectView(response.getP_NOTI() + "?resultCode=" + exception.getStatus());
        }

        InicisPayResult inicisPayResult = saveApprovalResult(approvalResponse);
        log.debug("이니시스 모바일 인증결과 수신 데이터 저장");

        Booking booking = null;
        try {
            if (inicisPayResult.isSuccess()) {
                log.debug("{}번 예약 결제 성공" , inicisPayResult.getNo());
                booking = savePaymentInfo(inicisPayResult);
                log.debug("{}번 예약 결제 완료 정보 저장 및 sms 메세지 보내기 성공" , inicisPayResult.getNo());
            }
        } catch (Exception exception) {
            Booking errorBooking = cancelInicis(inicisPayResult, exception);

            String redirectUrl = response.getP_NOTI() + "?resultCode=500&orderNumber=" + errorBooking.getNo();
            return new RedirectView(redirectUrl);
        }

        sendSmsMessage(Optional.ofNullable(booking));

        sendSlackAlarm(booking.getNo());

        String redirectUrl = response.getP_NOTI() + "?resultCode=" + inicisPayResult.getResultCode() + "&orderNumber=" + inicisPayResult.getOrderNo();

        log.debug("이니시스 모바일 결과 프론트 전송: {}", redirectUrl);
        return new RedirectView(redirectUrl);
    }

    private Booking cancelInicis(InicisPayResult inicisPayResult, Exception exception) {
        Booking errorBooking = bookingService.findByNo(inicisPayResult.getOrderNo());
        log.error("이니시스 결제결과 저장 중 오류(bookingNo: {})", errorBooking.getNo(), exception);

        log.debug("이니시스 결제 취소 요청...");
        cancelInicisPayment(inicisPayResult, errorBooking);
        log.debug("이니시스 결제 취소 완료");

        return errorBooking;
    }

    private InicisPayResult saveApprovalResult(InicisApprovalMobileResponse approvalResponse) {
        return inicisService.saveApprovalInfo(approvalResponse.toEntity());
    }

    private InicisApprovalMobileResponse requestInicisApproval(InicisMobileCertificationResponse response) {
        return inicisService.requestApprovalMobile(response);
    }

    private void sendSlackAlarm(final Long bookingNo) {
        slackService.sendBookingAlarm(bookingNo);
    }

}
