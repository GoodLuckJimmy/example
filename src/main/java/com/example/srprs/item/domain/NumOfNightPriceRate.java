package com.example.srprs.item.domain;

import java.math.BigDecimal;

public class NumOfNightPriceRate extends ItemPriceRate {
    private ItemPrice itemPrice;
    private int numOfNight;

    public NumOfNightPriceRate(ItemPrice itemPrice, int numOfNight) {
        this.itemPrice = itemPrice;
        this.numOfNight = numOfNight;
    }

    @Override
    public BigDecimal cost() {
        return itemPrice.cost().multiply(BigDecimal.valueOf(numOfNight));
    }
}
