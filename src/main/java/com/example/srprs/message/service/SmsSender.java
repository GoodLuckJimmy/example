package com.example.srprs.message.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.example.srprs.booking.domain.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class SmsSender {
    private final AmazonSNS sns;

    public void sendPaidMessage(final String phoneNumber) {
        String message = "감사합니다!" +
                " 브링프라이스 상품 결제가 완료되었습니다." +
                " 문의사항은 카카오톡에서 “브링프라이스”를 검색해주세요." +
                " http://pf.kakao.com/_cyZhM";

        sendMessage(phoneNumber, message);
    }

    private void sendMessage(String phoneNumber, String message) {
        if (phoneNumber == null) {
            log.debug("전화번호가 없습니다.");
            return;
        }

        try {
            PublishResult result = sns.publish(new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber("+82" + parsePhoneNumber(phoneNumber)));

            log.debug("sms 발송 to {}: {}", parsePhoneNumber(phoneNumber), result.getMessageId());
        } catch (Exception exception) {
            log.error("sms 보내기 오류", exception);
        }
    }

    private String parsePhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replace("-", "");

        if (phoneNumber.startsWith("0")) {
            return phoneNumber.substring(1);
        }

        return phoneNumber;
    }

    public void sendMorningNotice(Booking booking) {
        sendMessage(booking.getPhoneNumber(), booking.getSmsMessage());
    }
}
