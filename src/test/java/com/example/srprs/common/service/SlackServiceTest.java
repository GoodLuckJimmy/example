package com.example.srprs.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SlackServiceTest {
    private SlackService slackService;

    @BeforeEach
    void setup() {
        this.slackService = new SlackService();
        slackService.setup();
    }

    @Test
    void send() {
        // when
        String result = slackService.sendBookingAlarm(12L);

        // then
        assertThat(result).isEqualTo("ok");
    }

}