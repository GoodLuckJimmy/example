package com.example.srprs.admin.booking.dto;

import com.example.srprs.booking.domain.AccommodationType;
import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.domain.BookingStatusType;
import com.example.srprs.booking.domain.TransportationType;
import com.example.srprs.booking.dto.RequestExtraOptionDto;
import com.example.srprs.schedule.domain.Attachment;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UnscheduledBookingDto {
    @Schema(title = "예약번호", description = "예약 번호")
    private Long no;

    @Schema(title = "이메일")
    private String email;

    @Schema(title = "전화번호")
    private String phoneNumber;

    private AccommodationType accommodationType;

    @Schema(title = "성인 수", description = "예약한 성인 수")
    private int numOfAdult;

    @Schema(title = "아동 수", description = "예약한 아동 수")
    private int numOfChild;

    @Schema(title = "유아 수", description = "예약한 유아 수")
    private int numOfBaby;

    private TransportationType tripMethod;

    @Schema(title = "출발일", description = "출발일 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @Schema(title = "도착일", description = "도착일 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    @Schema(title = "예약자명", description = "예약자 이름")
    private String name;

    @Schema(title = "예약일", description = "예약일 yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingDate;

    @Schema(title = "예약 상태", description = "예약됨, 취소됨, 환불됨")
    private BookingStatusType status;

    @Schema(title = "상품 가격", description = "상품 가격. 옵션 가격 포함X")
    private BigDecimal price;

    @Schema(title = "고객 요청 사항", description = "고객이 추가로 요청한 사항")
    private String customerComment;

    @Schema(title = "md노트")
    private String mdNote;

    @Schema(title = "알림톡", description = "sms에 보내지는 메시지")
    private String smsMessage;

    @Schema(title = "추가 서비스")
    private List<RequestExtraOptionDto> extraOptions;

    @Schema(title = "스케쥴")
    private List<BookingScheduleDto> schedules;

    @Schema(title = "첨부파일")
    private List<String> attachmentUrl;

    public UnscheduledBookingDto(final Booking booking, final List<RequestExtraOptionDto> options, final List<BookingScheduleDto> schedules, List<Attachment> attachmentUrls) {
        this.no = booking.getNo();
        this.email = booking.getEmail();
        this.phoneNumber = booking.getPhoneNumber();
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
        this.price = booking.getTotalPaid(); // todo: 필드명 변경 필요
        this.customerComment = booking.getCustomerComment();
        this.mdNote = booking.getMdNote();
        this.smsMessage = booking.getSmsMessage();
        this.extraOptions = options;
        this.schedules = schedules;

        // todo: 임시
        if (!attachmentUrls.isEmpty()) {
            Attachment attachment = attachmentUrls.get(attachmentUrls.size() - 1);
            this.attachmentUrl = attachment.getAttachmentUrl();
        }

    }
}
