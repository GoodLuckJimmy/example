package com.example.srprs.schedule.service;

import com.example.srprs.admin.schedule.dto.AttachmentRequest;
import com.example.srprs.admin.schedule.dto.ScheduleRequest;
import com.example.srprs.exception.dto.NoContentException;
import com.example.srprs.schedule.domain.Attachment;
import com.example.srprs.schedule.domain.BookingSchedule;
import com.example.srprs.schedule.domain.BookingSchedulePath;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import com.example.srprs.schedule.dto.BookingSchedulePathDto;
import com.example.srprs.schedule.repository.AttachmentRepository;
import com.example.srprs.schedule.repository.BookingSchedulePathRepository;
import com.example.srprs.schedule.repository.BookingScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleService {
    private final BookingScheduleRepository bookingScheduleRepository;
    private final BookingSchedulePathRepository bookingSchedulePathRepository;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public void register(ScheduleRequest request) {
        request.getSchedules().forEach(bookingScheduleRequest -> {
            BookingSchedule bookingSchedule = BookingSchedule.builder()
                    .bookingNo(request.getBookingNo())
                    .cityName(bookingScheduleRequest.getCityName())
                    .date(bookingScheduleRequest.getDate())
                    .build();

            BookingSchedule savedBookingSchedule = bookingScheduleRepository.save(bookingSchedule);

            bookingScheduleRequest.getPaths().forEach(pathDto -> {
                BookingSchedulePath bookingSchedulePath = BookingSchedulePath.builder()
                        .bookingScheduleNo(savedBookingSchedule.getNo())
                        .time(pathDto.getTime())
                        .title(pathDto.getTitle())
                        .schedule(pathDto.getSchedule())
                        .build();

                bookingSchedulePathRepository.save(bookingSchedulePath);
            });

        });
    }

    @Transactional
    public void attach(AttachmentRequest request) {
        Attachment attachment = Attachment.builder()
                .bookingNo(request.getBookingNo())
                .attachmentUrl(request.getAttachmentUrls())
                .build();

        Attachment save = attachmentRepository.save(attachment);
        log.debug("첨부파일 저장{}", save.getNo());
    }

    @Transactional
    public void changeAttachment(AttachmentRequest request) {
        List<Attachment> attachment = attachmentRepository.findByBookingNo(request.getBookingNo());
        Attachment lastAttachment = attachment.get(attachment.size() - 1);

        lastAttachment.setAttachmentUrl(getLastAttachments(attachment));

    }

    @Transactional
    public void delete(ScheduleRequest request) {
        List<BookingSchedule> bookingSchedules = bookingScheduleRepository.findByBookingNo(request.getBookingNo());
        List<Long> bookingSchedulesIds = bookingSchedules.stream().map(BookingSchedule::getNo).collect(Collectors.toList());
        bookingScheduleRepository.deleteAll(bookingSchedules);

        List<BookingSchedulePath> bookingSchedulePaths = bookingSchedulePathRepository.findAllByBookingScheduleNoIn(bookingSchedulesIds);
        bookingSchedulePathRepository.deleteAll(bookingSchedulePaths);
    }

    @Transactional
    public void changeSchedule(ScheduleRequest request) {
        delete(request);
        register(request);
    }


    public List<BookingScheduleDto> getSchedule(Long bookingNo) {
        List<BookingSchedule> schedules = bookingScheduleRepository.findByBookingNo(bookingNo); // todo: 쿼리 한번에 가져오기
        List<Attachment> attachments = attachmentRepository.findByBookingNo(bookingNo); // todo: 예약과 첨부파일 1:1 대응으로 변경

        return schedules.stream().map(bookingSchedule -> {
            List<BookingSchedulePathDto> pathDtos = getBookingSchedulePathDtos(bookingSchedule);

            return BookingScheduleDto.builder()
                    .date(bookingSchedule.getDate())
                    .cityName(bookingSchedule.getCityName())
                    .paths(pathDtos)
                    .attachmentUrls(getLastAttachments(attachments))
                    .build();

        }).collect(Collectors.toList());

    }

    private  List<String>  getLastAttachments(List<Attachment> attachments) {
        if (attachments.isEmpty()) {
            return Collections.emptyList();
        }
        return attachments.get(attachments.size() - 1).getAttachmentUrl();
    }


    private List<BookingSchedulePathDto> getBookingSchedulePathDtos(BookingSchedule bookingSchedule) {
        List<BookingSchedulePath> paths = bookingSchedulePathRepository.findByBookingScheduleNo(bookingSchedule.getNo());
        return paths.stream()
                .map(path -> new BookingSchedulePathDto(path.getTime(), path.getTitle(), path.getSchedule()))
                .collect(Collectors.toList());
    }
}
