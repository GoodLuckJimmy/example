package com.example.srprs.item.domain;

import java.math.BigDecimal;

public class AccommodationPrice extends ItemPrice {

    public AccommodationPrice() {
        super();
    }

    @Override
    public BigDecimal cost() {
        return BigDecimal.valueOf(100_000);
    }
}
