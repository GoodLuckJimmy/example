package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.TransportationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestTripInfo {
    private TransportationType transportationType;

    private AccommodationType accommodationType;

    @Schema(title = "여행 인원", description = "여행자 종류별 인원")
    private NumOfTripper numOfTripper;

    @Schema(title = "추가 서비스", description = "추가 구매 서비스")
    private List<RequestExtraOptionDto> extraOptionsDto;

    @Schema(title = "추가 요청 사항", description = "고객이 추가로 요청")
    private String comment;

    public RequestTripInfo(TransportationType transportationType,
                           NumOfTripper numOfTripper,
                           String comment,
                           AccommodationType accommodationType,
                           List<RequestExtraOptionDto> extraOptionsDto) {

        this.accommodationType = accommodationType;
        this.transportationType = transportationType;
        this.numOfTripper = numOfTripper;
        this.comment = comment;
        this.extraOptionsDto = extraOptionsDto;
    }
}
