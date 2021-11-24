package com.example.srprs.item.domain;

import com.example.srprs.booking.domain.TransportationType;

import java.math.BigDecimal;

public class TransportationPriceRate extends ItemPriceRate {
    private ItemPrice itemPrice;
    private int numOfPeople;
    private TransportationType transportationType;

    public TransportationPriceRate(ItemPrice itemPrice, int numOfPeople, TransportationType transportationType) {
        super();
        this.itemPrice = itemPrice;
        this.numOfPeople = numOfPeople;
        this.transportationType = transportationType;
    }

    @Override
    public BigDecimal cost() {
        if (transportationType.equals(TransportationType.MY_CAR)) {
            return itemPrice.cost();
        }
        return itemPrice.cost().add(BigDecimal.valueOf(numOfPeople).multiply(BigDecimal.valueOf(50_000)));
    }
}
