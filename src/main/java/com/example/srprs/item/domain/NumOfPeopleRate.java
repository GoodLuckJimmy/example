package com.example.srprs.item.domain;

import java.math.BigDecimal;

public class NumOfPeopleRate extends ItemPriceRate{
    private ItemPrice itemPrice;
    private int numOfPeople;

    public NumOfPeopleRate(ItemPrice itemPrice, int numOfPeople) {
        super();
        this.itemPrice = itemPrice;
        this.numOfPeople = numOfPeople;
    }

    @Override
    public BigDecimal cost() {
        if (numOfPeople % 2 == 0) {
            return itemPrice.cost().multiply((BigDecimal.valueOf(numOfPeople/2)));
        }
        return itemPrice.cost().multiply((BigDecimal.valueOf(numOfPeople/2).add(BigDecimal.ONE)));
    }
}
