package com.example.srprs.schedule.domain;

import com.example.srprs.common.repository.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_booking_schedule_booking_no", columnList = "bookingNo DESC")
})
public class BookingSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long bookingNo;

    private LocalDate date;

    private String cityName;
}
