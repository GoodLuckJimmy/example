package com.example.srprs.booking.service;

import com.example.srprs.admin.booking.dto.BookingUnscheduledSearchResponse;
import com.example.srprs.admin.booking.dto.MdNoteRequest;
import com.example.srprs.admin.booking.dto.SmsMessageRequest;
import com.example.srprs.admin.booking.dto.UnscheduledBookingDto;
import com.example.srprs.booking.domain.*;
import com.example.srprs.booking.dto.*;
import com.example.srprs.booking.repository.BookingRepository;
import com.example.srprs.booking.repository.RequestExtraOptionRepository;
import com.example.srprs.exception.dto.BookingCancelException;
import com.example.srprs.exception.dto.NoBookingFoundException;
import com.example.srprs.exception.dto.NoContentException;
import com.example.srprs.exception.dto.WrongCancelRequest;
import com.example.srprs.schedule.domain.Attachment;
import com.example.srprs.schedule.dto.BookingScheduleDto;
import com.example.srprs.schedule.repository.AttachmentRepository;
import com.example.srprs.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RequestExtraOptionRepository requestExtraOptionRepository;
    private final ScheduleService scheduleService;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public Long book(BookingRequest request) {
        Booking booking = bookingRequestToEntity(request);
        Booking savedBooking = bookingRepository.save(booking);

        List<RequestExtraOptionDto> extraOptionsDto = request.getRequestTripInfo().getExtraOptionsDto();
        List<RequestExtraOption> requestExtraOptions = requestExtraOptionDTOToEntity(extraOptionsDto, savedBooking.getNo());
        requestExtraOptionRepository.saveAll(requestExtraOptions);

        return savedBooking.getNo();
    }

    private List<RequestExtraOption> requestExtraOptionDTOToEntity(List<RequestExtraOptionDto> extraOptionsDto, Long bookingNo) {
        return extraOptionsDto.stream()
                .map(dto -> {
            return RequestExtraOption.builder()
                    .bookingNo(bookingNo)
                    .optionType(dto.getOptionType())
                    .optionPrice(dto.getOptionPrice())
                    .build();
        }).collect(Collectors.toList());
    }

    private Booking bookingRequestToEntity(BookingRequest request) {
        return Booking.builder()
                .accommodationType(request.getRequestTripInfo().getAccommodationType())
                .numOfAdult(request.getRequestTripInfo().getNumOfTripper().getNumOfAdult())
                .numOfChild(request.getRequestTripInfo().getNumOfTripper().getNumOfChild())
                .numOfBaby(request.getRequestTripInfo().getNumOfTripper().getNumOfBaby())
                .departureDate(request.getPeriod().getDepartureDate())
                .arrivalDate(request.getPeriod().getArrivalDate())
                .tripMethod(request.getRequestTripInfo().getTransportationType())
                .name(request.getBookerInfo().getName())
                .gender(request.getBookerInfo().getGender())
                .email(request.getBookerInfo().getEmail())
                .phoneNumber(request.getBookerInfo().getPhoneNumber())
                .customerComment(request.getRequestTripInfo().getComment())
                .item(new BookingItem(null, request.getItemInfo().getPrice()))
                .status(new BookingStatus())
                .customerNo(request.getCustomerNo())
                .channel(request.getChannel())
                .build();
    }

    public BookingSearchResponse find(BookingSearchRequest request) {
        List<BookingHistoryDto> bookingHistoryDtos = bookingRepository.find(request);

        return new BookingSearchResponse(bookingHistoryDtos);
    }

    @Transactional
    public void cancel(Booking booking) {
        booking.getStatus().setCancel(true);
        booking.getStatus().setCancelDate(LocalDateTime.now());
        booking.getStatus().setBookingStatusType(BookingStatusType.CANCELED);

        bookingRepository.save(booking);
    }

    @Transactional
    public Booking findByNo(Long bookingNo) {
        return bookingRepository.findById(bookingNo)
                .orElseThrow(() -> new NoContentException("없는 예약입니다."));
    }

    public void validateCancelFee(BigDecimal requestCancelFee, Booking booking) {
        BigDecimal expectedCancelFee = calculateCancelFee(booking);

        if (requestCancelFee.compareTo(expectedCancelFee) != 0) {
            throw new WrongCancelRequest("잘못된 수수료입니다.");
        }
    }


    @Transactional
    public Booking paid(Long bookingNo, Long paymentNo, BigDecimal totalPaid) {
        Booking booking = findByNo(bookingNo);
        booking.paid(paymentNo, totalPaid);
        return booking;
    }

    public Booking find(Long bookingNo, Long customerNo) {
        return bookingRepository.findByNoAndCustomerNo(bookingNo, customerNo)
                .orElseThrow(() -> new NoBookingFoundException(bookingNo + "는 없는 예약입니다."));
    }

    public BookingHistoryDto findOne(Long bookingNo) {
        Booking booking = bookingRepository.findById(bookingNo)
                .orElseThrow(() -> new NoBookingFoundException(bookingNo + "는 없는 예약입니다."));

        // todo: 임시
        List<Attachment> attachments = attachmentRepository.findByBookingNo(bookingNo);
        List<String> attachmentUrl = new ArrayList<>();
        if (!attachments.isEmpty()) {
            attachmentUrl = attachments.get(attachments.size() - 1).getAttachmentUrl();
        }


        List<RequestExtraOption> options = requestExtraOptionRepository.findByBookingNo(bookingNo);
        List<RequestExtraOptionDto> optionDtos = options.stream()
                .map(RequestExtraOptionDto::new)
                .collect(Collectors.toList());

        return new BookingHistoryDto(booking, optionDtos, attachmentUrl);
    }

    public BigDecimal calculateCancelFee(final Booking booking) {
        if (booking.isRefundable()) {
            return BigDecimal.ZERO;
        }

        return Optional.ofNullable(booking.getTotalPaid())
                .orElseThrow(() -> new BookingCancelException(booking.getNo() + "번 예약의 결제 정보가 없습니다."));
    }

    @Transactional
    public void refund(Booking booking) {
        booking.getStatus().setRefund(true);
        booking.getStatus().setRefundDate(LocalDateTime.now());
        booking.getStatus().setBookingStatusType(BookingStatusType.REFUNDED);

        bookingRepository.save(booking);
    }

    @Transactional
    public void refundByException(Booking booking) {
        booking.getStatus().setRefund(true);
        booking.getStatus().setRefundDate(LocalDateTime.now());
        booking.getStatus().setBookingStatusType(BookingStatusType.ERROR_REFUNDED);

        bookingRepository.save(booking);
    }

    @Transactional
    public BookingUnscheduledSearchResponse findAllNoScheduled() {
        // todo: 쿼리 한번에 가져오기
        BookingSearcher bookingSearcher = BookingSearcher.builder()
                .statusType(BookingStatusType.RESERVED)
//                .hasSchedule(Boolean.FALSE)
                .build();
        List<Booking> bookings = bookingRepository.find(bookingSearcher);


        List<UnscheduledBookingDto> result = bookings.stream().map(booking -> {
            List<RequestExtraOptionDto> options = requestExtraOptionRepository.findByBookingNo(booking.getNo()).stream()
                    .map(RequestExtraOptionDto::new)
                    .collect(Collectors.toList());
            List<BookingScheduleDto> schedules = scheduleService.getSchedule(booking.getNo());
            List<Attachment> attachmentUrls = attachmentRepository.findByBookingNo(booking.getNo());

            return new UnscheduledBookingDto(booking, options, schedules, attachmentUrls);
        }).collect(Collectors.toList());

        return new BookingUnscheduledSearchResponse(result);
    }

    @Transactional
    public void registerSmsMessage(SmsMessageRequest request) {
        Booking booking = findByNo(request.getBookingNo());
        booking.setSmsMessage(request.getMessage());
    }

    @Transactional
    public void registerMdNote(MdNoteRequest request) {
        Booking booking = findByNo(request.getBookingNo());
        booking.setMdNote(request.getMessage());
    }

    public List<Booking> todayDepartureBooking() {
        return bookingRepository.findTodayDepartureBooking();
    }

    @Transactional
    public Booking findByNo(String bookingNo) {
        return findByNo(Long.valueOf(bookingNo));
    }
}
