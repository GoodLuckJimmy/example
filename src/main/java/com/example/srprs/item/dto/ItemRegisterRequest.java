package com.example.srprs.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ItemRegisterRequest {
    @Schema(title = "출발일", description="여행 출발일")
    private LocalDate departureDate;

    @Schema(title = "도착일", description="여행 도착일")
    private LocalDate arrivalDate;

    @Schema(title = "note", description="참고 사항")
    private String remark;

    @Schema(title = "가격", description="상품 가격")
    private BigDecimal price;

    @Schema(title = "인원수", description="전체 인원수")
    private int numOfPeople;

    @Builder
    public ItemRegisterRequest(LocalDate departureDate, LocalDate arrivalDate, String remark, BigDecimal price, int numOfPeople) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.remark = remark;
        this.price = price;
        this.numOfPeople = numOfPeople;
    }
}
