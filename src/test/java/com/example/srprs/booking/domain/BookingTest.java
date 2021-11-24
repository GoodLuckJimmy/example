package com.example.srprs.booking.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class BookingTest {

    @Test
    void isRefundable() {
        assertThat(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().minusDays(1)) > 1).isFalse();
        assertThat(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now()) > 1).isFalse();
        assertThat(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusDays(1)) > 1).isFalse();

        assertThat(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusDays(2)) > 1).isTrue();
    }

}