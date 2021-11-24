package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.RequestExtraOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RequestExtraOptionDto {
    private RequestOptionType optionType;

    @Schema(title = "추가 서비스 가격", description = "추가 구매 가능한 서비스 가격")
    private BigDecimal optionPrice;

    public RequestExtraOptionDto(RequestOptionType optionType, BigDecimal optionPrice) {
        this.optionType = optionType;
        this.optionPrice = optionPrice;
    }

    public RequestExtraOptionDto(RequestExtraOption option) {
        this.optionType = option.getOptionType();
        this.optionPrice = option.getOptionPrice();
    }
}
