package com.example.srprs.booking.dto;

import com.example.srprs.booking.domain.*;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class BookingHistoryDto {
    @Schema(title = "예약번호", description = "예약 번호")
    private Long no;

    @Schema(title = "예약자 번호", description = "예약자 번호")
    private Long customerNo;

    private AccommodationType accommodationType;

    @Schema(title = "성인 수", description = "예약한 성인 수")
    private int numOfAdult;

    @Schema(title = "아동 수", description = "예약한 아동 수")
    private int numOfChild;

    @Schema(title = "유아 수", description = "예약한 유아 수")
    private int numOfBaby;

    private TransportationType tripMethod;

    @Schema(title = "출발일", description = "출발일 yyyy-MM-dd")
    private LocalDate departureDate;

    @Schema(title = "도착일", description = "도착일 yyyy-MM-dd")
    private LocalDate arrivalDate;

    @Schema(title = "예약자명", description = "예약자 이름")
    private String name;

    @Schema(title = "예약일", description = "예약일 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingDate;

    @Schema(title = "예약 상태", description = "예약됨, 취소됨, 환불됨")
    private BookingStatusType status;

    @Schema(title = "상품 가격", description = "상품 가격. 옵션 가격 포함X")
    private BigDecimal price;

    @Schema(title = "채널")
    private Channel channel;

    @Schema(title = "고객 요청 사항", description = "고객이 추가로 요청한 사항")
    private String customerComment;

    @Schema(title = "추가 서비스")
    private List<RequestExtraOptionDto> extraOptions;

    @Schema(title = "스캐줄")
    private List<BookingScheduleDto> schedules;

    @Schema(title = "첨부파일 url")
    private List<String> attachmentUrl;

    public BookingHistoryDto(final Booking booking, final List<RequestExtraOptionDto> options, List<String> attachmentUrl) {
        this.no = booking.getNo();
        this.customerNo = booking.getCustomerNo();
        this.accommodationType = booking.getAccommodationType();
        this.numOfAdult = booking.getNumOfAdult();
        this.numOfChild = booking.getNumOfChild();
        this.numOfBaby = booking.getNumOfBaby();
        this.tripMethod = booking.getTripMethod();
        this.departureDate = booking.getDepartureDate();
        this.arrivalDate = booking.getArrivalDate();
        this.name = booking.getName();
        this.bookingDate = booking.getModDate();
        this.status = booking.getStatus().getBookingStatusType();
        this.price = booking.getItem().getPrice();
        this.customerComment = booking.getCustomerComment();
        this.extraOptions = options;
        this.attachmentUrl = attachmentUrl;
        this.channel = booking.getChannel();
    }

    public Channel getChannel() {
        return Optional.ofNullable(this.channel)
                .orElse(Channel.BP);
    }
}
