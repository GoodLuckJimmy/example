package com.example.srprs.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.srprs.admin.schedule.dto.BookingScheduleRequest;
import com.example.srprs.admin.schedule.dto.ScheduleRequest;
import com.example.srprs.booking.domain.RequestExtraOption;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.dto.RequestOptionType;
import com.example.srprs.schedule.domain.Attachment;
import com.example.srprs.schedule.domain.BookingSchedule;
import com.example.srprs.schedule.domain.BookingSchedulePath;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import com.example.srprs.schedule.dto.BookingSchedulePathDto;
import com.example.srprs.schedule.repository.AttachmentRepository;
import com.example.srprs.schedule.repository.BookingSchedulePathRepository;
import com.example.srprs.schedule.repository.BookingScheduleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("test")
class ScheduleServiceTest {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    BookingScheduleRepository bookingScheduleRepository;

    @Autowired
    BookingSchedulePathRepository bookingSchedulePathRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    List<BookingScheduleRequest> schedules;

    @BeforeEach
    void setup() {
        List<BookingSchedulePathDto> paths = IntStream.rangeClosed(0, 9)
                .mapToObj(i -> new BookingSchedulePathDto(LocalTime.now().plusHours(i), "title" + i, "schedule" + i))
                .collect(Collectors.toList());

        schedules = IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new BookingScheduleRequest("city" + i, LocalDate.now().plusDays(i), paths))
                .collect(Collectors.toList());
    }


    @Test
    void register() {
        // given
        Long bookingNo = 1L;

        // when
        saveSchedule(bookingNo);

        // then
        List<BookingSchedule> schedules = bookingScheduleRepository.findByBookingNo(bookingNo);

        assertThat(schedules).isNotEmpty();

        for (int i = 0; i < schedules.size(); i++) {
            assertThat(schedules.get(i).getBookingNo()).isEqualTo(bookingNo);
            assertThat(schedules.get(i).getCityName()).isEqualTo("city" + i);
            assertThat(schedules.get(i).getDate()).isEqualTo(LocalDate.now().plusDays(i));

            List<BookingSchedulePath> paths = bookingSchedulePathRepository.findByBookingScheduleNo(schedules.get(i).getNo());
            assertPath(paths);
        }


    }

    private void assertPath(List<BookingSchedulePath> paths) {
        for (int j = 0; j < paths.size(); j++) {
            assertThat(paths.get(j).getTitle()).isEqualTo("title" + j);
            assertThat(paths.get(j).getSchedule()).isEqualTo("schedule" + j);
        }
    }

    private void saveSchedule(Long bookingNo) {
        ScheduleRequest request = new ScheduleRequest(bookingNo, schedules);
        scheduleService.register(request);
    }

    @Test
    @Transactional
    void getSchedule() {
        // given
        Long bookingNo = 1L;
        saveSchedule(bookingNo);
        saveAttachment(bookingNo);

        // when
        List<BookingScheduleDto> schedules = scheduleService.getSchedule(1L);

        // then
        assertThat(schedules).isNotEmpty();

        for (int i = 0; i < schedules.size(); i++) {
            assertThat(schedules.get(i).getPaths()).isNotEmpty();
            assertThat(schedules.get(i).getCityName()).isEqualTo("city" + i);
            assertThat(schedules.get(i).getAttachmentUrls().get(0)).isEqualTo("img");
        }
    }

    private void saveAttachment(Long bookingNo) {
        Attachment attachment = Attachment.builder()
                .bookingNo(bookingNo)
                .attachmentUrl(List.of("img"))
                .build();

        attachmentRepository.save(attachment);
    }

}