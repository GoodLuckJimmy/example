package com.example.srprs.admin.booking.api;

import com.example.srprs.admin.booking.dto.BookingUnscheduledSearchResponse;
import com.example.srprs.admin.booking.dto.MdNoteRequest;
import com.example.srprs.admin.booking.dto.SmsMessageRequest;
import com.example.srprs.admin.schedule.dto.ScheduleRequest;
import com.example.srprs.booking.dto.BookingHistoryDto;
import com.example.srprs.booking.service.BookingService;
import com.example.srprs.exception.dto.NotCorrectCustomerOrderException;
import com.example.srprs.member.dto.MemberDto;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="어드민", description = "예약 관련 api")
public class AdminBookingApi {
    private final BookingService bookingService;
    private final ScheduleService scheduleService;
    private final AttachmentRepository attachmentRepository; // todo: 임시

    @Operation(summary = "미일정 예약 조회", description = "일정이 등록되지 않은 예약만 조회한다. admin용 Authorization 헤더 필요")
    @GetMapping("/admin/bookings/unscheduled")
    public BookingUnscheduledSearchResponse searchUnscheduled() {
        return bookingService.findAllNoScheduled();
    }

    @Operation(summary = "알림톡 등록", description = "고객에게 보내는 SMS메시지 등록. admin용 Authorization 헤더 필요")
    @PostMapping("/admin/bookings/sms")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerSmsMessage(@RequestBody SmsMessageRequest request) {
        bookingService.registerSmsMessage(request);
    }

    @Operation(summary = "알림톡 수정", description = "고객에게 보내는 SMS메시지 수정. admin용 Authorization 헤더 필요")
    @PutMapping("/admin/bookings/sms")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void changeSmsMessage(@RequestBody SmsMessageRequest request) {
        bookingService.registerSmsMessage(request);
    }

    @Operation(summary = "MD 노트 등록", description = "MD 노트 등록. admin용 Authorization 헤더 필요")
    @PostMapping("/admin/bookings/md-note")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerMdNote(@RequestBody MdNoteRequest request) {
        bookingService.registerMdNote(request);
    }

    @Operation(summary = "MD 노트 수정", description = "MD 노트 수정. admin용 Authorization 헤더 필요")
    @PutMapping("/admin/bookings/md-note")
    @ResponseStatus(HttpStatus.OK)
    public void changeMdNote(@RequestBody MdNoteRequest request) {
        bookingService.registerMdNote(request);
    }

    @Operation(summary = "예약 한건 조회", description = "한 건의 예약을 조회한다. Authorization 헤더 필요")
    @GetMapping("/admin/bookings/histories/{bookingNo}")
    public BookingHistoryDto searchOneBooking(@Schema(title = "예약 번호") @PathVariable Long bookingNo) {
        BookingHistoryDto bookingHistoryDto = bookingService.findOne(bookingNo);

        putSchedule(bookingHistoryDto);

        return bookingHistoryDto;
    }

    // todo: 쿼리 한번에 조회. bookingApi의 putSchedule랑 중복
    private void putSchedule(BookingHistoryDto bookingHistoryDto) {
        List<BookingScheduleDto> schedules = scheduleService.getSchedule(bookingHistoryDto.getNo());
        List<Attachment> attachments = attachmentRepository.findByBookingNo(bookingHistoryDto.getNo());
        if (!attachments.isEmpty()) {
            Attachment attachment = attachments.get(attachments.size() - 1);
            bookingHistoryDto.setAttachmentUrl(attachment.getAttachmentUrl());
        }

        bookingHistoryDto.setSchedules(schedules);
    }

}
