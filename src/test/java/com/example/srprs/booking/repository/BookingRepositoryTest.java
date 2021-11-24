package com.example.srprs.booking.repository;

import com.example.srprs.booking.BookingDataMaker;
import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.domain.RequestExtraOption;
import com.example.srprs.booking.dto.BookingHistoryDto;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.dto.RequestExtraOptionDto;
import com.example.srprs.common.JPATestSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class BookingRepositoryTest extends JPATestSetting {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RequestExtraOptionRepository requestOptionRepository;

    @Test
    @Transactional
    void save() {
        // given
        Booking booking = BookingDataMaker.bookingWithoutNo();

        // when
        Booking savedBooking = bookingRepository.save(booking);
        RequestExtraOption requestExtraOption = BookingDataMaker.requestOption(savedBooking);
        RequestExtraOption savedRequestExtraOption = requestOptionRepository.save(requestExtraOption);

        // then
        assertThat(savedBooking.getNo()).isPositive();
        assertThat(savedBooking.getAccommodationType()).isEqualTo(booking.getAccommodationType());
        assertThat(savedBooking.getEmail()).isEqualTo(booking.getEmail());
        assertThat(savedBooking.getCustomerComment()).isEqualTo(booking.getCustomerComment());
        assertThat(savedBooking.getGender()).isEqualTo(booking.getGender());
        assertThat(savedBooking.getPhoneNumber()).isEqualTo(booking.getPhoneNumber());
        assertThat(savedBooking.getNumOfAdult()).isEqualTo(booking.getNumOfAdult());
        assertThat(savedBooking.getNumOfChild()).isEqualTo(booking.getNumOfChild());
        assertThat(savedBooking.getNumOfBaby()).isEqualTo(booking.getNumOfBaby());
        assertThat(savedBooking.getDepartureDate()).isEqualTo(booking.getDepartureDate());
        assertThat(savedBooking.getArrivalDate()).isEqualTo(booking.getArrivalDate());

        assertThat(savedRequestExtraOption.getNo()).isPositive();
        assertThat(savedRequestExtraOption.getOptionType()).isEqualTo(requestExtraOption.getOptionType());
        assertThat(savedRequestExtraOption.getOptionPrice()).isEqualTo(requestExtraOption.getOptionPrice());
        assertThat(savedRequestExtraOption.getBookingNo()).isEqualTo(savedBooking.getNo());

    }

    @Test
    void findByBookingSearchRequest() {
        // given
        Booking booking = BookingDataMaker.bookingWithoutNo();
        Booking savedBooking = bookingRepository.save(booking);

        List<RequestExtraOption> requestExtraOptions = BookingDataMaker.requestOptions(savedBooking);
        requestOptionRepository.saveAll(requestExtraOptions);


        // when
        BookingSearchRequest request = BookingDataMaker.bookingSearchRequest(savedBooking.getCustomerNo());
        List<BookingHistoryDto> bookingHistoryDtos = bookingRepository.find(request);

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
}