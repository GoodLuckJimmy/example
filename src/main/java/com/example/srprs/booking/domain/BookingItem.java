package com.example.srprs.booking.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class BookingItem {
    private Long itemNo;
    private BigDecimal price;

    public BookingItem(Long itemNo, BigDecimal price) {
        this.itemNo = itemNo;
        this.price = price;
    }
}
