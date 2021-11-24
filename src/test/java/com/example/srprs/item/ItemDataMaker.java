package com.example.srprs.item;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import com.example.srprs.item.domain.Item;
import com.example.srprs.item.dto.ItemCalendarDTO;
import com.example.srprs.item.dto.ItemRegisterRequest;
import com.example.srprs.item.dto.ItemCalendarSearchRequest;
import com.example.srprs.item.dto.ItemSearchResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ItemDataMaker {
    public static ItemCalendarSearchRequest itemCalendarSearchRequest() {
        return new ItemCalendarSearchRequest(8, 2, 1, AccommodationType.HOTEL, TransportationType.PUBLIC_TRANSIT);
    }

    public static ItemCalendarDTO itemDTO() {
        return new ItemCalendarDTO(123L, LocalDate.now(), BigDecimal.valueOf(10_000));
    }

    public static ItemRegisterRequest itemRegisterRequest() {
        return ItemRegisterRequest.builder()
                .departureDate(LocalDate.now())
                .arrivalDate(LocalDate.now().plusDays(1L))
                .price(BigDecimal.valueOf(10_000))
                .numOfPeople(2)
                .remark("강원도 감자농장 체험")
                .build();
    }

    public static Item item() {
        return Item.builder()
                .no(123L)
                .departureDate(LocalDate.now())
                .arrivalDate(LocalDate.now().plusDays(1L))
                .numOfNight(1)
                .isAvailable(true)
                .remark("강원도 감자농장 체험")
                .price(BigDecimal.valueOf(10_000))
                .numOfPeople(2)
                .build();
    }

    public static List<Item> itemsWithoutNo() {
        return IntStream.rangeClosed(1, 100).mapToObj(i -> {
            return Item.builder()
                    .departureDate(LocalDate.now())
                    .arrivalDate(LocalDate.now().plusDays(1L))
                    .numOfNight(1)
                    .isAvailable(true)
                    .remark("강원도 감자농장 체험 " + i + "번째")
                    .price(BigDecimal.valueOf(10_000))
                    .numOfPeople(2)
                    .build();
        }).collect(Collectors.toList());
    }

    public static ItemSearchResponse itemSearchResponse() {
        return new ItemSearchResponse(List.of(itemDTO()));
    }
}
