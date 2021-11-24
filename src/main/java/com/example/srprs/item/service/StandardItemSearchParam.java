package com.example.srprs.item.service;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import lombok.Getter;

@Getter
public class StandardItemSearchParam {
    private AccommodationType accommodationType;
    private int numOfPeople;
    private int numOfNight;
    private TransportationType transportationType;

    public StandardItemSearchParam(AccommodationType accommodationType,
                                   int numOfPeople,
                                   int numOfNight,
                                   TransportationType transportationType) {
        this.accommodationType = accommodationType;
        this.numOfPeople = numOfPeople;
        this.numOfNight = numOfNight;
        this.transportationType = transportationType;
    }
}
