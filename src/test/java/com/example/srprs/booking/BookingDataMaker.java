package com.example.srprs.booking;

import com.example.srprs.booking.domain.*;
import com.example.srprs.booking.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookingDataMaker {

    public static BookingRequest bookingRequest() {
        return BookingRequest.builder()
                .requestTripInfo(
                        new RequestTripInfo(
                                TransportationType.MY_CAR,
                                new NumOfTripper(2,0,0),
                                "2인분 같은 1인분",
                                AccommodationType.HOTEL,
                                requestOptions()))
                .period(new TripPeriod(LocalDate.now(), LocalDate.now().plusDays(1)))
                .bookerInfo(new BookerInfo(Gender.MALE, "test@test.com", "010123134", "김또깡"))
                .itemInfo(new ItemInfo(BigDecimal.valueOf(100_000)))
                .build();
    }

    public static Booking bookingWithNo() {
        Booking booking = bookingWithoutNo();
        booking.setNo(123L);
        return booking;
    }

    public static Booking bookingWithoutNo() {
        return Booking.builder()
                .accommodationType(AccommodationType.HOTEL)
                .numOfAdult(2)
                .numOfChild(2)
                .numOfBaby(1)
                .departureDate(LocalDate.now().plusDays(1))
                .arrivalDate(LocalDate.now())
                .gender(Gender.MALE)
                .email("test@test.com")
                .phoneNumber("0101231234")
                .tripMethod(TransportationType.MY_CAR)
                .status(new BookingStatus())
                .customerComment("서비스 만땅")
                .customerNo(123L)
                .build();
    }

    private static List<RequestExtraOptionDto> requestOptions() {
        return List.of(
                new RequestExtraOptionDto(RequestOptionType.BREAKFAST,BigDecimal.valueOf(10_000)),
                new RequestExtraOptionDto(RequestOptionType.PET, BigDecimal.valueOf(50_000)));
    }

    public static RequestExtraOption requestOption(Booking booking) {
        return RequestExtraOption.builder()
                .bookingNo(booking.getNo())
                .optionPrice(BigDecimal.valueOf(10_000))
                .optionType(RequestOptionType.BREAKFAST)
                .build();
    }

    public static List<RequestExtraOption> requestOptions(Booking booking) {
        return IntStream.rangeClosed(1, 10).mapToObj(i -> {
            return RequestExtraOption.builder()
                    .bookingNo(booking.getNo())
                    .optionPrice(BigDecimal.valueOf(10_000L * i))
                    .optionType(RequestOptionType.BREAKFAST)
                    .build();
        }).collect(Collectors.toList());
    }

    public static BookingSearchRequest bookingSearchRequest(Long customerNo) {
        return new BookingSearchRequest(customerNo);
    }

    public static BookingSearchResponse bookingSearchResponse() {
        List<BookingHistoryDto> bookingHistories = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            BookingHistoryDto dto = new BookingHistoryDto();
            dto.setNo((long) i);
            dto.setAccommodationType(AccommodationType.GUEST_HOUSE);
            dto.setNumOfAdult(i % 3 + 1);
            dto.setTripMethod(TransportationType.PUBLIC_TRANSIT);
            dto.setDepartureDate(LocalDate.now());
            dto.setArrivalDate(LocalDate.now().plusDays(3));
            dto.setBookingDate(LocalDateTime.now().minusDays(1));
            bookingHistories.add(dto);
        });

        return new BookingSearchResponse(bookingHistories);
    }
}
