package com.example.srprs.util;

import com.example.srprs.payment.dto.InicisApprovalMobileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjectMapperTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test() throws JsonProcessingException {
        String value = "P_STATUS=00&P_AUTH_DT=20210913193325&P_AUTH_NO=43290681&P_RMESG1=성공적으로 처리 하였습니다.&P_RMESG2=00&P_TID=INIMX_ISP_INIpayTest20210913193325455491&P_FN_CD1=11&P_AMT=1000&P_TYPE=CARD&P_UNAME=hong gildong&P_MID=INIpayTest&P_OID=168&P_NOTI=&P_NEXT_URL=http://10.120.120.65:5000/payments/inicis/certification/mobile&P_MNAME=&P_NOTEURL=&P_CARD_MEMBER_NUM=&P_CARD_NUM=920096*********2&P_CARD_ISSUER_CODE=00&P_CARD_PURCHASE_CODE=11&P_CARD_PRTC_CODE=1&P_CARD_INTEREST=0&P_CARD_CHECKFLAG=1&P_CARD_ISSUER_NAME=비씨카드&P_CARD_PURCHASE_NAME=BC카드&P_FN_NM=BC카드&CARD_CorpFlag=0&P_ISP_CARDCODE=040100969601371&P_CARD_APPLPRICE=1000&EventCode=";

        String[] values = new String(value).split("&");

        Map<String, String> hashMap = new HashMap<>();

        Arrays.asList(values).forEach(v -> {
            String[] split = v.split("=");
            if (split.length == 2) {
                hashMap.put(split[0], split[1]);
            }
        });


    }
}
