package com.example.srprs.item.domain;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class BusyDayRate extends ItemPriceRate{
    private ItemPrice itemPrice;
    private LocalDate day;

    public BusyDayRate(ItemPrice itemPrice, LocalDate day) {
        super();
        this.itemPrice = itemPrice;
        this.day = day;
    }

    @Override
    public BigDecimal cost() {
        if (isHoliday() || isWeekend()) {
            return itemPrice.cost().multiply(BigDecimal.valueOf(1.5));
        }

        return this.itemPrice.cost();
    }

    private boolean isHoliday() {
        List<LocalDate> holidays = List.of(
                LocalDate.of(2021, 9, 19),
                LocalDate.of(2021, 9, 20),
                LocalDate.of(2021, 9, 21),
                LocalDate.of(2021, 10, 3),
                LocalDate.of(2021, 10, 10)
        );

        return holidays.contains(day);
    }

    private boolean isWeekend() {
        return day.getDayOfWeek().equals(DayOfWeek.FRIDAY)
                || day.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }
}
