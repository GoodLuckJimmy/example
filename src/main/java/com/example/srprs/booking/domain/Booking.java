package com.example.srprs.booking.domain;

import com.example.srprs.common.repository.BaseEntity;
import com.example.srprs.member.dto.MemberDto;
import com.example.srprs.util.StringAttributeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccommodationType accommodationType;

    private int numOfAdult;

    private int numOfChild;

    private int numOfBaby;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TransportationType tripMethod;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    @Convert(converter = StringAttributeConverter.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Convert(converter = StringAttributeConverter.class)
    private String email;

    @Convert(converter = StringAttributeConverter.class)
    private String phoneNumber;

    @Column(length = 300)
    private String customerComment;

    @Embedded
    private BookingStatus status;

    @Embedded
    private BookingItem item;

    private Long scheduleNo;

    private Long customerNo;

    private Long paymentNo;

    @Column(length = 500)
    private String smsMessage;

    @Column(length = 300)
    private String mdNote;

    private BigDecimal totalPaid;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Channel channel;

    public boolean isRefundable() {
        LocalDate now = LocalDate.now();
        // 하루전이면 100% 환불
        return ChronoUnit.DAYS.between(now, this.departureDate) > 1;
    }

    public void paid(Long paymentNo, BigDecimal totalPaid) {
        this.paymentNo = paymentNo;
        this.totalPaid = totalPaid;
        this.status.setBookingStatusType(BookingStatusType.RESERVED);
    }

    public boolean isCorrectCustomer(MemberDto memberDto) {
        return this.customerNo.equals(memberDto.getNo());
    }
}
