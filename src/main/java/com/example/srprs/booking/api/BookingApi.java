package com.example.srprs.booking.api;

import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.dto.*;
import com.example.srprs.booking.service.BookingService;
import com.example.srprs.common.service.SlackService;
import com.example.srprs.exception.dto.NotCorrectCustomerOrderException;
import com.example.srprs.member.dto.MemberDto;
import com.example.srprs.payment.service.PaymentService;
import com.example.srprs.schedule.domain.Attachment;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import com.example.srprs.schedule.repository.AttachmentRepository;
import com.example.srprs.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="예약", description = "예약 관련 api")
public class BookingApi {
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final ScheduleService scheduleService;
    private final SlackService slackService;
    private final AttachmentRepository attachmentRepository; // todo: 임시

    @Operation(summary = "여행상품 가예약", description = "결제전 여행상품을 가예약한다. Authorization 헤더 필요")
    @PostMapping("/booking")
    public BookingResponse book(@Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDTO,
                     @Valid @RequestBody BookingRequest request) {
        request.setCustomerNo(memberDTO.getNo());
        request.getBookerInfo().setEmail(
                Optional.ofNullable(request.getBookerInfo().getEmail())
                        .orElse(memberDTO.getId())
        );

        return new BookingResponse(bookingService.book(request));
    }

    @Operation(summary = "예약 조회", description = "예약을 조회한다. Authorization 헤더 필요")
    @GetMapping("/booking/histories")
    public BookingSearchResponse search(@Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDto) {

        BookingSearchResponse response = bookingService.find(new BookingSearchRequest(memberDto.getNo()));
        response.getBookingHistories().forEach(this::putSchedule);

        return response;
    }

    @Operation(summary = "예약 한건 조회", description = "한 건의 예약을 조회한다. Authorization 헤더 필요")
    @GetMapping("/booking/histories/{bookingNo}")
    public BookingHistoryDto searchOneBooking(@Schema(title = "예약 번호") @PathVariable Long bookingNo,
                                              @Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDto) {
        BookingHistoryDto bookingHistoryDto = bookingService.findOne(bookingNo);

        if (!bookingHistoryDto.getCustomerNo().equals(memberDto.getNo())) {
            throw new NotCorrectCustomerOrderException(
                    "customer(" + memberDto.getNo() + ")가 다른 고객의 예약(No: " + bookingNo + ")을 읽으려고 접근했습니다.");
        }

        putSchedule(bookingHistoryDto);

        return bookingHistoryDto;
    }

    // todo: 쿼리 한번에 조회
    private void putSchedule(BookingHistoryDto bookingHistoryDto) {
        List<BookingScheduleDto> schedules = scheduleService.getSchedule(bookingHistoryDto.getNo());
        List<Attachment> attachments = attachmentRepository.findByBookingNo(bookingHistoryDto.getNo());
        if (!attachments.isEmpty()) {
            Attachment attachment = attachments.get(attachments.size() - 1);
            bookingHistoryDto.setAttachmentUrl(attachment.getAttachmentUrl());
        }

        bookingHistoryDto.setSchedules(schedules);
    }

    @Operation(summary = "예약 취소 수수료 조회", description = "예약 취소 수수료 조회. Authorization 헤더 필요")
    @GetMapping("/booking/fee")
    public BookingCancelFeeResponse cancelFee(@Valid BookingCancelFeeRequest request,
                                              @Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDTO) {
        Booking booking = bookingService.find(request.getBookingNo(), memberDTO.getNo());
        BigDecimal cancelFee = bookingService.calculateCancelFee(booking);

        return new BookingCancelFeeResponse(booking.getNo(), cancelFee);
    }

    @Operation(summary = "예약 취소", description = "예약을 취소한다. Authorization 헤더 필요")
    @DeleteMapping("/booking")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void cancel(@Valid @RequestBody BookingCancelRequest request,
                       @Parameter(hidden = true) @AuthenticationPrincipal MemberDto memberDto) {
        log.debug("{}번 예약 삭제", request.getBookingNo());

        Booking booking = bookingService.findByNo(request.getBookingNo());

        if (!booking.isCorrectCustomer(memberDto)) {
            throw new NotCorrectCustomerOrderException(
                    "customer(" + memberDto.getNo() + ")가 다른 고객(" + booking.getCustomerNo() + ")의 예약을 삭제하려고 접근했습니다.");
        }

        bookingService.validateCancelFee(request.getCancelFee(), booking);

        bookingService.cancel(booking);

        paymentService.refund(booking.getPaymentNo());

        bookingService.refund(booking);

        slackService.sendCancelAlarm(booking.getNo());
    }
}
