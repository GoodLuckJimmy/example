package com.example.srprs.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ItemSearchResponse {
    @Schema(title = "상품정보")
    private List<ItemCalendarDTO> items;

    public ItemSearchResponse(List<ItemCalendarDTO> items) {
        this.items = items;
    }
}
