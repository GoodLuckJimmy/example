package com.example.srprs.item.dto;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemSearchParam {
    private AccommodationType accommodationType;
    private int numOfPeople;
    private TransportationType transportationType;
    private LocalDate day;

    public ItemSearchParam(AccommodationType accommodationType,
                           int numOfPeople,
                           TransportationType transportationType,
                           LocalDate day) {
        this.accommodationType = accommodationType;
        this.numOfPeople = numOfPeople;
        this.transportationType = transportationType;
        this.day = day;
    }
}
