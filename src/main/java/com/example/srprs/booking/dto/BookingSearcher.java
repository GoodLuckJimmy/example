package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.BookingStatusType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingSearcher {
    private BookingStatusType statusType;
    private Boolean hasSchedule;
}
