package com.example.srprs.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StaticItemSearchResponse {
    @Schema(title = "상품 가격 리스트", description = "요청월 한달 전후 포함한 3달치 가격. 특정 날짜 요청시 그날짜만")
    private List<CalendarItemPrice> itemPrices = new ArrayList<>();

    public StaticItemSearchResponse(List<CalendarItemPrice> itemPrices) {
        this.itemPrices = itemPrices;
    }
}
