package com.example.srprs.admin.schedule.api;

import com.example.srprs.admin.schedule.dto.AttachmentRequest;
import com.example.srprs.admin.schedule.dto.ScheduleRequest;
import com.example.srprs.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="어드민", description = "예약 관련 api")
public class AdminScheduleApi {
    private final ScheduleService scheduleService;

    @Operation(summary = "일정 등록", description = "일정 등록. admin용 Authorization 헤더 필요")
    @PostMapping("/admin/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerSchedule(@RequestBody ScheduleRequest request) {
        scheduleService.register(request);
    }

    @Operation(summary = "일정 수정", description = "일정 수정. admin용 Authorization 헤더 필요")
    @PutMapping("/admin/schedules")
    @ResponseStatus(HttpStatus.OK)
    public void changeSchedule(@RequestBody ScheduleRequest request) {
        scheduleService.changeSchedule(request);
    }

    @Operation(summary = "예약 첨부파일 등록", description = "교통 숙박 표등 첨부 파일 저장. admin용 Authorization 헤더 필요")
    @PostMapping("/admin/attachments")
    @ResponseStatus(HttpStatus.CREATED)
    public void attach(@RequestBody AttachmentRequest request) {
        scheduleService.attach(request);
    }

    @Operation(summary = "예약 첨부파일 수정", description = "교통 숙박 표등 첨부 파일 수정. admin용 Authorization 헤더 필요")
    @PutMapping("/admin/attachments")
    @ResponseStatus(HttpStatus.OK)
    public void changeAttachment(@RequestBody AttachmentRequest request) {
        scheduleService.changeAttachment(request);
    }

}
