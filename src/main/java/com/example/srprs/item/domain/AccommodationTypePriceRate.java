package com.example.srprs.item.domain;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.exception.UnsupportedAccommodationException;

import java.math.BigDecimal;

public class AccommodationTypePriceRate extends ItemPriceRate {
    private ItemPrice itemPrice;
    private AccommodationType accommodationType;

    public AccommodationTypePriceRate(ItemPrice itemPrice, AccommodationType accommodationType) {
        super();
        this.itemPrice = itemPrice;
        this.accommodationType = accommodationType;
    }

    @Override
    public BigDecimal cost() {
        switch (accommodationType) {
            case HOTEL:
                return itemPrice.cost();
            case RESORT:
                return itemPrice.cost().multiply(BigDecimal.valueOf(2));
            case GUEST_HOUSE:
                return itemPrice.cost().multiply(BigDecimal.valueOf(0.6));
            case PREMIUM_HOTEL:
                return itemPrice.cost().multiply(BigDecimal.valueOf(10));
            default:
                throw new UnsupportedAccommodationException();
        }
    }
}
