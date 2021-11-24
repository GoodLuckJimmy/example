package com.example.srprs.admin.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AttachmentRequest {
    @Schema(title = "예약 번호")
    private Long bookingNo;

    @Schema(title = "첨부파일 url")
    private List<String> attachmentUrls;
}
