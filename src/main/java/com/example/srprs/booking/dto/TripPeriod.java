package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TripPeriod {
    @Schema(title = "여행 출발일", description = "여행 출발일")
    private LocalDate departureDate;

    @Schema(title = "여행 도착일", description = "여행 도착일")
    private LocalDate arrivalDate;

    public TripPeriod(LocalDate departureDate, LocalDate tripTo) {
        this.departureDate = departureDate;
        this.arrivalDate = tripTo;
    }
}