package com.example.srprs.item.dto;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Schema(title = "요청 파라미터")
public class ItemCalendarOneDaySearchRequest {
    @Schema(title = "특정 날짜", description = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate day;

    @Schema(title = "인원", description = "최소 1명. 최대 8명")
    @Min(1) @Max(8)
    private int numOfPeople;

    @Schema(title = "체류기간(박)", description = "몇 박 묵을 건지. 최소 1박 최대 4박")
    @Min(1) @Max(4)
    private int numOfNight;

    private AccommodationType accommodationType;

    private TransportationType transportationType;

    @Builder
    public ItemCalendarOneDaySearchRequest(LocalDate day, int numOfPeople, int numOfNight,
                                           AccommodationType accommodationType, TransportationType transportationType) {
        this.day = day;
        this.numOfPeople = numOfPeople;
        this.numOfNight = numOfNight;
        this.accommodationType = accommodationType;
        this.transportationType = transportationType;
    }
}

