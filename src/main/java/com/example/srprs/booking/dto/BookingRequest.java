package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequest {
    @Schema(title = "여행 정보", description = "여행상품 관련 요청")
    private RequestTripInfo requestTripInfo;

    @Schema(title = "여행 기간", description = "여행 기간 관련 요청")
    private TripPeriod period;

    @Valid
    @Schema(title = "예약자 정보 ", description = "예약자 정보 요청")
    private BookerInfo bookerInfo;

    @Schema(title = "상품 정보", description = "상품 정보")
    private ItemInfo itemInfo;

    @Schema(hidden = true, title = "회원번호")
    private Long customerNo;

    private Channel channel;

    @Builder
    public BookingRequest(RequestTripInfo requestTripInfo, TripPeriod period, BookerInfo bookerInfo, ItemInfo itemInfo, Channel channel) {
        this.requestTripInfo = requestTripInfo;
        this.period = period;
        this.bookerInfo = bookerInfo;
        this.itemInfo = itemInfo;
        this.channel = channel;
    }

    public Channel getChannel() {
        return Optional.ofNullable(this.channel)
                .orElse(Channel.BP);
    }
}
