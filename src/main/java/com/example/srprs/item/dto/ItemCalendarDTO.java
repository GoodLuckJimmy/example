package com.example.srprs.item.dto;

import com.example.srprs.item.domain.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ItemCalendarDTO {
    @Schema(title = "상품번호", description = "상품 정보")
    private Long itemNo;

    @Schema(title = "출발일", description = "여행 출발일")
    private LocalDate departureDate;

    @Schema(title = "가격", description = "가격")
    private BigDecimal price;

    public ItemCalendarDTO(Long itemNo, LocalDate departureDate, BigDecimal price) {
        this.itemNo = itemNo;
        this.departureDate = departureDate;
        this.price = price;
    }

    public static ItemCalendarDTO of(final Item item) {
        return new ItemCalendarDTO(item.getNo(), item.getDepartureDate(), item.getPrice());
    }
}
