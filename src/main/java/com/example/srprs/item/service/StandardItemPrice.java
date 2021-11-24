package com.example.srprs.item.service;

import com.example.srprs.item.domain.ItemPrice;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StandardItemPrice {
    private BigDecimal weekPrice;
    private BigDecimal weekendPrice;

    public StandardItemPrice(ItemPrice price) {
        this.weekPrice = price.cost();
        this.weekendPrice = price.cost().multiply(BigDecimal.valueOf(1.5));
    }
}
