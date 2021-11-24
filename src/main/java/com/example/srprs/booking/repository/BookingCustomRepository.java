package com.example.srprs.booking.repository;

import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.dto.BookingHistoryDto;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.dto.BookingSearcher;

import java.util.List;

public interface BookingCustomRepository {
    List<BookingHistoryDto> find(BookingSearchRequest request);

    List<Booking> find(BookingSearcher bookingSearcher);

    List<Booking> findTodayDepartureBooking();
}
