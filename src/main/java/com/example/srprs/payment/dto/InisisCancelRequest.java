package com.example.srprs.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@Slf4j
public class InisisCancelRequest {
    private String type = "Refund";

    private String iniapiKey;

    private String paymethod = "Card";

    private String clientIp = "218.235.22.194"; // 성수 스파크 플러스

    private String mid;

    private String tid;

    @JsonProperty("msg")
    private String message = "거래취소요청";

    private String hashData;

    private String timestamp;

    public InisisCancelRequest(String mid, String tid, LocalDateTime date) {
        this.mid = mid;
        this.tid = tid;
        this.timestamp = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    @SneakyThrows
    public String getHashData() {
        String raw = iniapiKey + type + paymethod + timestamp + clientIp + mid + tid;

        MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(raw.getBytes());

        return String.format("%0128x", new BigInteger(1, md.digest()));

    }
}
