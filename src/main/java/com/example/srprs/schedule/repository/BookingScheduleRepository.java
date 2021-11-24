package com.example.srprs.schedule.repository;

import com.example.srprs.schedule.domain.BookingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingScheduleRepository extends JpaRepository<BookingSchedule, Long> {
    List<BookingSchedule> findByBookingNo(Long bookingNo);

}
