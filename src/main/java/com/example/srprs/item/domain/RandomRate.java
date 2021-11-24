package com.example.srprs.item.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class RandomRate extends ItemPriceRate {
    private ItemPrice itemPrice;
    private LocalDate day;

    public RandomRate(ItemPrice itemPrice, LocalDate day) {
        super();
        this.itemPrice = itemPrice;
        this.day = day;
    }

    @Override
    public BigDecimal cost() {
        return this.itemPrice.cost().add(randomPrice());
    }

    private BigDecimal randomPrice() {
        // 25,000 사이의 금액 랜덤 추가
        Random random = new Random(day.toEpochDay());
        return BigDecimal.valueOf(random.nextInt(26)).multiply(BigDecimal.valueOf(1000));
    }
}
