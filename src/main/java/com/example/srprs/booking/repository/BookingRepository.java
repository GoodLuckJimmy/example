package com.example.srprs.booking.repository;

import com.example.srprs.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, BookingCustomRepository {
    Optional<Booking> findByNoAndCustomerNo(Long bookingNo, Long customerNo);
}
