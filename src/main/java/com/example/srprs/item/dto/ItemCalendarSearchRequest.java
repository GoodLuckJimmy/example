package com.example.srprs.item.dto;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@NoArgsConstructor
@Getter
@Setter
@Schema(title = "요청 파라미터")
public class ItemCalendarSearchRequest {
    @Schema(title = "월", description = "1 ~ 12월")
    @Min(1) @Max(12)
    private int month;

    @Schema(title = "인원")
    @Min(1) @Max(8)
    private int numOfPeople;

    @Schema(title = "체류기간(박)", description = "몇 박 묵을 건지")
    @Min(1) @Max(4)
    private int numOfNight;

    private AccommodationType accommodationType;

    private TransportationType transportationType;

    @Builder
    public ItemCalendarSearchRequest(int month, int numOfPeople, int numOfNight,
                                     AccommodationType accommodationType, TransportationType transportationType) {
        this.month = month;
        this.numOfPeople = numOfPeople;
        this.numOfNight = numOfNight;
        this.accommodationType = accommodationType;
        this.transportationType = transportationType;
    }
}

