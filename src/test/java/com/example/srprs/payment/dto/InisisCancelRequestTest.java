package com.example.srprs.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

class InisisCancelRequestTest {

    @Test
    void hash() {
        // given
        InisisCancelRequest request = new InisisCancelRequest();
        request.setIniapiKey("ItEQKi3rY7uvDS8l");
        request.setType("Refund");
        request.setPaymethod("Card");
        request.setTimestamp("20191128121211");
        request.setClientIp("123.123.123.123");
        request.setMid("INIpayTest");
        request.setTid("StdpayCARDINIpayTest20191128121211123456");

        // then
        assertThat(request.getHashData()).isEqualTo("b2dc4d4308d836a77187fa1f4ce8c540006a41e6a708a63aded363510c7d445600601c9035825fe32f48fe1b7d2ea130f690a2895a41b6fa0a99c6c5f92d6d69");
    }

}