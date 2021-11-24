package com.example.srprs.booking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumOfTripper {
    @Schema(title = "성인", description = "성인 수")
    private int numOfAdult;

    @Schema(title = "아동", description = "아동 수(만 2 ~ 17세)")
    private int numOfChild;

    @Schema(title = "유아", description = "유아 수(0 ~ 23개월)")
    private int numOfBaby;

    public NumOfTripper(int numOfAdult, int numOfChild, int numOfBaby) {
        this.numOfAdult = numOfAdult;
        this.numOfChild = numOfChild;
        this.numOfBaby = numOfBaby;
    }
}
