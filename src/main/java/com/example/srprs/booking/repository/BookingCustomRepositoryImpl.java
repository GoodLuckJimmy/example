package com.example.srprs.booking.repository;

import com.example.srprs.booking.domain.Booking;
import com.example.srprs.booking.domain.BookingStatusType;
import com.example.srprs.booking.dto.BookingHistoryDto;
import com.example.srprs.booking.dto.BookingSearchRequest;
import com.example.srprs.booking.dto.BookingSearcher;
import com.example.srprs.booking.dto.RequestExtraOptionDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.srprs.booking.domain.QBooking.booking;
import static com.example.srprs.booking.domain.QRequestExtraOption.requestExtraOption;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class BookingCustomRepositoryImpl implements BookingCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookingHistoryDto> find(BookingSearchRequest request) {
        return queryFactory.from(booking)
                .leftJoin(requestExtraOption)
                .on(booking.no.eq(requestExtraOption.bookingNo))
                .where(
                        booking.customerNo.eq(request.getCustomerNo())
                        .and(booking.status.bookingStatusType.ne(BookingStatusType.PRE_RESERVED)))
                .transform(
                        groupBy(booking.no).list(
                                Projections.fields(
                                        BookingHistoryDto.class,
                                        booking.no,
                                        booking.accommodationType,
                                        booking.numOfAdult,
                                        booking.numOfChild,
                                        booking.numOfBaby,
                                        booking.tripMethod,
                                        booking.departureDate,
                                        booking.arrivalDate,
                                        booking.name,
                                        booking.regDate.as("bookingDate"),
                                        booking.status.bookingStatusType.as("status"),
                                        booking.item.price,
                                        booking.channel,
                                        booking.customerComment,
                                        list(
                                                Projections.fields(
                                                        RequestExtraOptionDto.class,
                                                        requestExtraOption.optionType,
                                                        requestExtraOption.optionPrice
                                                )
                                        ).as("extraOptions")
                                )
                        )
                );
    }

    @Override
    public List<Booking> find(BookingSearcher bookingSearcher) {
        return queryFactory.selectFrom(booking)
                .where(
                        eqBookingStatusType(bookingSearcher.getStatusType()),
                        hasSchedule(bookingSearcher.getHasSchedule())
                )
                .orderBy(booking.no.desc())
                .fetch();
    }

    @Override
    public List<Booking> findTodayDepartureBooking() {
        return queryFactory.selectFrom(booking)
                .where(
                        eqBookingStatusType(BookingStatusType.RESERVED),
                        eqDepartureDate(LocalDate.now())
                )
                .orderBy(booking.no.desc())
                .fetch();
    }

    private Predicate eqDepartureDate(LocalDate departureDate) {
        return booking.departureDate.eq(departureDate);
    }

    private Predicate hasSchedule(Boolean hasSchedule) {
        if (hasSchedule == null) return null;
        if (hasSchedule.equals(Boolean.TRUE)) {
            return booking.scheduleNo.isNotNull();
        }
        return booking.scheduleNo.isNull();
    }

    private Predicate eqBookingStatusType(BookingStatusType statusType) {
        if (statusType == null) return null;
        return booking.status.bookingStatusType.eq(statusType);
    }

}
