package com.example.srprs.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.srprs.config.WebMvcConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;

@JsonTest
@Import(WebMvcConfig.class)
class InicisApprovalResponseTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void inicisDataParsing() throws JsonProcessingException {

        // when
        InicisApprovalWebResponse response = objectMapper.readValue(rawString, InicisApprovalWebResponse.class);

        // then
        assertThat(response.getBuyerTel()).isEqualTo("010-1234-5678");
        assertThat(response.getResultCode()).isEqualTo("0000");
        assertThat(response.getResultMessage()).isEqualTo("정상완료");
        assertThat(response.getTid()).isEqualTo("StdpayISP_INIpayTest20210909105959652099");
    }

    private final static String rawString =
            "{\n" +
                    "  \"CARD_Quota\": \"00\",\n" +
                    "  \"CARD_ClEvent\": \"\",\n" +
                    "  \"CARD_CorpFlag\": \"0\",\n" +
                    "  \"buyerTel\": \"010-1234-5678\",\n" +
                    "  \"parentEmail\": \"\",\n" +
                    "  \"applDate\": \"20210909\",\n" +
                    "  \"buyerEmail\": \"jimmy@example.com\",\n" +
                    "  \"p_Sub\": \"\",\n" +
                    "  \"resultCode\": \"0000\",\n" +
                    "  \"mid\": \"INIpayTest\",\n" +
                    "  \"CARD_UsePoint\": \"\",\n" +
                    "  \"CARD_Num\": \"920096*********2\",\n" +
                    "  \"authSignature\": \"20a9396b41a546b06aa91c21e504aeaa40c5a10739ad561d1657a33e9de495d6\",\n" +
                    "  \"ISP_CardCode\": \"040100969601371\",\n" +
                    "  \"tid\": \"StdpayISP_INIpayTest20210909105959652099\",\n" +
                    "  \"EventCode\": \"\",\n" +
                    "  \"goodName\": \"테스트\",\n" +
                    "  \"TotPrice\": \"1000\",\n" +
                    "  \"payMethod\": \"VCard\",\n" +
                    "  \"CARD_MemberNum\": \"\",\n" +
                    "  \"MOID\": \"1234\",\n" +
                    "  \"CARD_Point\": \"\",\n" +
                    "  \"currency\": \"WON\",\n" +
                    "  \"CARD_PurchaseCode\": \"\",\n" +
                    "  \"CARD_PrtcCode\": \"1\",\n" +
                    "  \"applTime\": \"110000\",\n" +
                    "  \"goodsName\": \"테스트\",\n" +
                    "  \"CARD_CheckFlag\": \"1\",\n" +
                    "  \"FlgNotiSendChk\": \"\",\n" +
                    "  \"CARD_Code\": \"11\",\n" +
                    "  \"CARD_BankCode\": \"00\",\n" +
                    "  \"CARD_TerminalNum\": \"019058I000\",\n" +
                    "  \"ISP_RetrievalNum\": \"\",\n" +
                    "  \"P_FN_NM\": \"BC카드\",\n" +
                    "  \"buyerName\": \"홍길동\",\n" +
                    "  \"p_SubCnt\": \"\",\n" +
                    "  \"applNum\": \"44786837\",\n" +
                    "  \"resultMsg\": \"정상완료\",\n" +
                    "  \"CARD_Interest\": \"0\",\n" +
                    "  \"CARD_SrcCode\": \"\",\n" +
                    "  \"CARD_ApplPrice\": \"1000\",\n" +
                    "  \"CARD_GWCode\": \"G\",\n" +
                    "  \"custEmail\": \"jimmy@example.com\",\n" +
                    "  \"CARD_PurchaseName\": \"BC카드\",\n" +
                    "  \"CARD_PRTC_CODE\": \"1\",\n" +
                    "  \"payDevice\": \"PC\"\n" +
                    "}";

}