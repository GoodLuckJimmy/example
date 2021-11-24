package com.example.srprs.booking.service;

import com.example.srprs.booking.BookingDataMaker;
import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.domain.RequestExtraOption;
import com.example.srprs.booking.dto.*;
import com.example.srprs.booking.repository.BookingRepository;
import com.example.srprs.booking.repository.RequestExtraOptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RequestExtraOptionRepository requestExtraOptionRepository;

    @Test
    void booking() {
        // given
        BookingRequest request = BookingDataMaker.bookingRequest();

        // when
        Long bookingId = bookingService.book(request);
        List<RequestExtraOption> options = requestExtraOptionRepository.findByBookingNo(bookingId);

        // then
        assertThat(bookingId).isPositive();
        options.forEach(option -> {
            assertThat(option.getBookingNo()).isEqualTo(bookingId);
        });
    }

    @Test
    void find() {
        // given
        Booking booking = BookingDataMaker.bookingWithoutNo();
        Booking savedBooking = bookingRepository.save(booking);

        List<RequestExtraOption> requestExtraOptions = BookingDataMaker.requestOptions(savedBooking);
        requestExtraOptionRepository.saveAll(requestExtraOptions);


        // when
        BookingSearchRequest request = BookingDataMaker.bookingSearchRequest(savedBooking.getCustomerNo());
        BookingSearchResponse bookingSearchResponse = bookingService.find(request);
        List<BookingHistoryDto> bookingHistoryDtos = bookingSearchResponse.getBookingHistories();

        // then
        assertThat(bookingHistoryDtos).isNotEmpty();

        BookingHistoryDto bookingHistoryDTO = bookingHistoryDtos.get(0);
        assertThat(bookingHistoryDTO.getNo()).isEqualTo(savedBooking.getNo());
        assertThat(bookingHistoryDTO.getAccommodationType()).isEqualTo(savedBooking.getAccommodationType());
        assertThat(bookingHistoryDTO.getNumOfAdult()).isEqualTo(savedBooking.getNumOfAdult());
        assertThat(bookingHistoryDTO.getNumOfChild()).isEqualTo(savedBooking.getNumOfChild());
        assertThat(bookingHistoryDTO.getNumOfBaby()).isEqualTo(savedBooking.getNumOfBaby());
        assertThat(bookingHistoryDTO.getTripMethod()).isEqualTo(savedBooking.getTripMethod());
        assertThat(bookingHistoryDTO.getDepartureDate()).isEqualTo(savedBooking.getDepartureDate());
        assertThat(bookingHistoryDTO.getArrivalDate()).isEqualTo(savedBooking.getArrivalDate());

        assertThat(bookingHistoryDTO.getExtraOptions()).hasSize(requestExtraOptions.size());
        RequestExtraOptionDto requestExtraOptionDTO = bookingHistoryDTO.getExtraOptions().get(0);
        assertThat(requestExtraOptionDTO.getOptionType()).isEqualTo(requestExtraOptions.get(0).getOptionType());
        assertThat(requestExtraOptionDTO.getOptionPrice().doubleValue()).isEqualTo(requestExtraOptions.get(0).getOptionPrice().doubleValue());
    }

    @Test
    void findTodayDepartureBooking() {
        List<Booking> bookings = bookingService.todayDepartureBooking();
    }
}