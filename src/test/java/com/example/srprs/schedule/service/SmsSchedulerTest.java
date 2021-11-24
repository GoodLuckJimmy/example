package com.example.srprs.schedule.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SmsSchedulerTest {
    @Autowired
    SmsScheduler smsScheduler;

    @Test
//    @Disabled
    void sendSms() {
        smsScheduler.sendSms();
    }
}