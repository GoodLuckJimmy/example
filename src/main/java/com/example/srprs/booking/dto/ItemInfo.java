package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class ItemInfo {
    // todo: 필드명 변경 필요
    @Schema(title = "최종 상품 가격", description = "최종 상품 가격")
    private BigDecimal price;

    public ItemInfo(BigDecimal price) {
        this.price = price;
    }
}
