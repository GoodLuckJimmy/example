package com.example.srprs.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(
            HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
        log.debug("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
        ClientHttpResponse response = ex.execute(req, reqBody);
        InputStreamReader isr = new InputStreamReader(
                response.getBody(), StandardCharsets.UTF_8);
        String body = new BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"));
        log.debug("Response body: {}", body);
        return response;
    }
}
