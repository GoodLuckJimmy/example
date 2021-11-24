package com.example.srprs.booking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingSearchRequest {
    private Long customerNo;

    public BookingSearchRequest(Long customerNo) {
        this.customerNo = customerNo;
    }
}
