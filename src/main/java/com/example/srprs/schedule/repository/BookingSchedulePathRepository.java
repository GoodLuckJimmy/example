package com.example.srprs.schedule.repository;

import com.example.srprs.schedule.domain.BookingSchedulePath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSchedulePathRepository extends JpaRepository<BookingSchedulePath, Long> {
    List<BookingSchedulePath> findAllByBookingScheduleNoIn(List<Long> bookingSchedulesIds);

    List<BookingSchedulePath> findByBookingScheduleNo(Long bookingScheduleNo);
}
