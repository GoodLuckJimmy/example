package com.example.srprs.common.service;

import com.example.srprs.common.dto.SlackMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SlackService {
    private RestTemplate restTemplate;
    private static final String HOOK_URL = "https://hooks.slack.com/services/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";

    @Value("${spring.config.activate.on-profile}")
    private String profile;

    @PostConstruct
    public void setup() {
        restTemplate = new RestTemplate();
    }

    public String sendBookingAlarm(Long bookingNo) {
        String contents = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ": 예약이 들어왔습니다!!(Booking NO: " + bookingNo + ")";

        SlackMessage message = SlackMessage.builder()
                .text(contents)
                .build();

        return send(message);
    }

    private String send(SlackMessage message) {
        if (!"prod".equals(profile)) {
            return "ok";
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("username", message.getUsername());
        payload.put("text", message.getText());

        try {
            return restTemplate.postForObject(HOOK_URL, payload, String.class);
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    public String sendCancelAlarm(Long bookingNo) {
        String contents = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ": 예약이 취소되었습니다(Booking NO: " + bookingNo + ")";

        SlackMessage message = SlackMessage.builder()
                .text(contents)
                .build();

        return send(message);
    }
}
