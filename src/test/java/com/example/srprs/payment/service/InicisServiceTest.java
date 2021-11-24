package com.example.srprs.payment.service;

import com.example.srprs.common.config.LoggingInterceptor;
import com.example.srprs.payment.dto.InisisCancelRequest;
import com.example.srprs.payment.dto.InisisCancelResponse;
import com.example.srprs.payment.repository.InicisPayResultRepository;
import com.example.srprs.payment.repository.InisisCancelResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InicisServiceTest {
    private InicisService inicisService;

    @Mock
    private InicisPayResultRepository inicisResultRepository;

    @Mock
    private InisisCancelResultRepository inisisCancelResultRepository;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ClientHttpRequestFactory factory =
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());

        RestTemplate restTemplate = new RestTemplate(factory);

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);

        this.inicisService = new InicisService(
                objectMapper,
                inicisResultRepository,
                inisisCancelResultRepository,
                restTemplate);

        this.inicisService.setCancelUrl("https://iniapi.inicis.com/api/v1/refund");
    }

    @Test
    void cancelRequest() {
        String tid = "StdpayCARDINIpayTest20210101111111111111";
        String mid = "INIpayTest";
        InisisCancelRequest request = new InisisCancelRequest(mid , tid, LocalDateTime.now());
        request.setIniapiKey("ItEQKi3rY7uvDS8l");

        InisisCancelResponse inisisCancelResponse = inicisService.cancelRequest(tid);

        System.out.println(inisisCancelResponse.getResultMessage());
        assertThat(inisisCancelResponse.getResultCode()).isEqualTo("00");
    }

}