package com.example.srprs.schedule.service;

import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.service.BookingService;
import com.example.srprs.message.service.SmsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsScheduler {
    private final BookingService bookingService;
    private final SmsSender smsSender;

    @Value("${spring.config.activate.on-profile}")
    private String profile;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendSms() {
        if (!"prod".equals(profile)) {
            return;
        }

        log.info("sms를 보냅니다...");

        List<Booking> bookings = bookingService.todayDepartureBooking();

        AtomicInteger count = new AtomicInteger(0);
        bookings.forEach(booking -> {
            if (booking.getSmsMessage() != null && booking.getPhoneNumber() != null) {
                smsSender.sendMorningNotice(booking);
                count.getAndIncrement();
            }
        });

        count.getAndIncrement();

        log.info("sms를 {}/{}건 보냈습니다. bookingNo: {}", count.get(), bookings.size());
    }

}
