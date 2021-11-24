package com.example.srprs.item.domain;

import com.example.srprs.common.repository.BaseEntity;
import com.example.srprs.item.dto.ItemRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private int numOfNight;

    private boolean isAvailable;

    private String remark;

    private BigDecimal price;

    private int numOfPeople;

    @Embedded
    private Accommodation accommodation;

    public static Item of(ItemRegisterRequest request) {
        return Item.builder()
                .departureDate(request.getDepartureDate())
                .arrivalDate(request.getArrivalDate())
                .numOfNight(Period.between(request.getDepartureDate(), request.getArrivalDate()).getDays())
                .remark(request.getRemark())
                .price(request.getPrice())
                .isAvailable(true)
                .numOfPeople(request.getNumOfPeople())
                .build();
    }
}
