package com.example.srprs.payment.service;

import com.example.srprs.exception.dto.AlgorithmException;
import com.example.srprs.exception.dto.InicisException;
import com.example.srprs.exception.dto.MobilePaymentException;
import com.example.srprs.exception.dto.NoPaymentInfoException;
import com.example.srprs.payment.domain.InicisPayResult;
import com.example.srprs.payment.domain.InisisCancelResult;
import com.example.srprs.payment.dto.*;
import com.example.srprs.payment.repository.InicisPayResultRepository;
import com.example.srprs.payment.repository.InisisCancelResultRepository;
import com.example.srprs.util.MultiValueMapConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicis.std.util.HttpUtil;
import com.inicis.std.util.SignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InicisService {
    private final ObjectMapper objectMapper;
    private final InicisPayResultRepository inicisResultRepository;
    private final InisisCancelResultRepository inisisCancelResultRepository;

    @Qualifier("inicisRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${inicis.cancel-url}")
    @Setter
    private String cancelUrl;

    @Value("${inicis.mid}")
    private String mid;

    @Value("${inicis.signKey}")
    private String signKey;

    @Value("${inicis.iniapiKey}")
    private String iniapiKey;

    public InicisApprovalWebResponse approvalWeb(InicisWebCertificationResponse response) {
        String timestamp= SignatureUtil.getTimestamp();

        String signature = createSignature(response, timestamp);

        Map<String, String> authMap = createMessageMap(response, timestamp, signature);
        String responseMessage = sendWebApprovalMessage(response, authMap);

        try {
            return objectMapper.readValue(responseMessage, InicisApprovalWebResponse.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new InicisException(e.getMessage());
        }
    }


    private String sendWebApprovalMessage(InicisWebCertificationResponse response, Map<String, String> authMap) {
        HttpUtil httpUtil = new HttpUtil();

        try {
            log.info("##승인요청 API 요청...##");
            return httpUtil.processHTTP(authMap, response.getAuthUrl());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InicisException("이니시스 전문 송신중 오류: " + e.getMessage());
        }
    }

    private Map<String, String> createMessageMap(InicisWebCertificationResponse response, String timestamp, String signature) {
        Map<String, String> authMap = new Hashtable<>();
        authMap.put("mid", response.getMid());               // 필수
        authMap.put("authToken", response.getAuthToken());  // 필수
        authMap.put("signature", signature);     // 필수
        authMap.put("timestamp", timestamp);  // 필수
        authMap.put("charset", response.getCharset());         // default=UTF-8
        authMap.put("format", "JSON");          // default=XML

        return authMap;
    }

    private String createSignature(InicisWebCertificationResponse response, String timestamp) {
        Map<String, String> signParam = new HashMap<>();

        signParam.put("authToken", response.getAuthToken());// 필수
        signParam.put("timestamp", timestamp);// 필수

        // signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
        try {
            return SignatureUtil.makeSignature(signParam);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InicisException("이니시스 시그니처 생성 중 오류: " + e.getMessage());
        }
    }

    public String getMKey() {
        try {
            return SignatureUtil.hash(signKey, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AlgorithmException("이니시스 해시 오류");
        }
    }

    public String getSignature(String orderNo, String timeStamp, BigDecimal price) throws Exception {
        Map<String, String> signParam = new HashMap<>();
        signParam.put("oid", orderNo);
        signParam.put("price", price.toPlainString());
        signParam.put("timestamp", timeStamp);

        return SignatureUtil.makeSignature(signParam);
    }

    @Transactional
    public InicisPayResult saveApprovalInfo(InicisPayResult entity) {
        return inicisResultRepository.save(entity);
    }

    public InisisCancelResponse cancelRequest(String tid) {
        InisisCancelRequest inisisCancelRequest = new InisisCancelRequest(mid, tid, LocalDateTime.now());

        if (inisisCancelRequest.getIniapiKey() == null) {
            inisisCancelRequest.setIniapiKey(this.iniapiKey);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Accept", "*");

        MultiValueMap<String, String> parameters = MultiValueMapConverter.convert(objectMapper, inisisCancelRequest);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        return restTemplate.exchange(cancelUrl, HttpMethod.POST, request, InisisCancelResponse.class).getBody();
    }


    public InicisPayResult find(Long paymentNo) {
        return inicisResultRepository.findById(paymentNo)
                .orElseThrow(() -> new NoPaymentInfoException("결제 정보를 찾을 수 없습니다."));
    }

    public void saveCancelResult(InisisCancelResult inisisCancelResult) {
        inisisCancelResultRepository.save(inisisCancelResult);
    }

    public InicisApprovalMobileResponse requestApprovalMobile(InicisMobileCertificationResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=euc_kr");
        HttpEntity request = new HttpEntity(headers);

        String mobileApprovalUrl = response.getP_REQ_URL() + "?P_TID=" + response.getP_TID() + "&P_MID=" + this.mid;

        String exchange = restTemplate.exchange(mobileApprovalUrl, HttpMethod.POST, request, String.class).getBody();

        // todo:
        String[] values = exchange.split("&");

        Map<String, String> hashMap = new HashMap<>();

        Arrays.asList(values).forEach(v -> {
            String[] split = v.split("=");
            if (split.length == 2) {
                hashMap.put(split[0], split[1]);
            }
        });

        if (!"00".equals(hashMap.get("P_STATUS"))) {
            throw new MobilePaymentException(hashMap.get("P_STATUS"), hashMap.get("P_RMESG1"));
        }

        InicisApprovalMobileResponse result = new InicisApprovalMobileResponse();
        result.setResultCode(hashMap.get("P_STATUS"));
        result.setResultMessage(hashMap.get("P_RMESG1"));
        result.setTid(hashMap.get("P_TID"));
        result.setPayMethod(hashMap.get("P_TYPE"));
        result.setApplyDate(LocalDateTime.parse(hashMap.get("P_AUTH_DT"), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        result.setMid(hashMap.get("P_MID"));
        result.setOrderNo(hashMap.get("P_OID"));
        result.setTotalPrice(BigDecimal.valueOf(Long.valueOf(hashMap.get("P_AMT"))));
        result.setBuyerName(hashMap.get("P_UNAME"));
        result.setMname(hashMap.get("P_MNAME"));
        result.setMerchantData(hashMap.get("P_NOTI"));
        result.setMerchantDataUrl(hashMap.get("P_NOTEURL"));
        result.setMerchantNextUrl(hashMap.get("P_NEXT_URL"));
        result.setPartCancelAvailability(Integer.valueOf(hashMap.get("P_CARD_PRTC_CODE")));
        result.setCardFlag(hashMap.get("CARD_CorpFlag"));
        result.setCardKind(hashMap.get("P_CARD_CHECKFLAG"));
        result.setCardQuota(Integer.valueOf(hashMap.get("P_RMESG2")));
        result.setCardNumber(hashMap.get("P_CARD_NUM"));
        result.setAuthNumber(hashMap.get("P_AUTH_NO"));
        result.setCardPaidPrice(BigDecimal.valueOf(Long.valueOf(hashMap.get("P_CARD_APPLPRICE"))));
        if (hashMap.get("P_CARD_USEPOINT") != null) {
            result.setPointUsed(BigDecimal.valueOf(Long.valueOf(hashMap.get("P_CARD_USEPOINT"))));
        }
        if (hashMap.get("P_COUPON_DISCOUNT") != null) {
            result.setCouponDiscount(BigDecimal.valueOf(Long.valueOf(hashMap.get("P_COUPON_DISCOUNT"))));
        }

        return result;
    }
}
