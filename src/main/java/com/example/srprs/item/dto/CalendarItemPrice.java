package com.example.srprs.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class CalendarItemPrice {
    @Schema(title = "날짜", description = "yyyy-MM-dd")
    private LocalDate date;

    @Schema(title = "가격")
    private BigDecimal price;

    public CalendarItemPrice(LocalDate date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public BigDecimal getPrice() {
        // 200만원 이상시 품절처리
        return price.compareTo(BigDecimal.valueOf(2_000_000L)) > 0 ?
                BigDecimal.ZERO : this.price;
    }
}
