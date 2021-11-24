package com.example.srprs.schedule.domain;

import com.example.srprs.common.repository.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(indexes = {
        @Index(name = "idx_booking_schedule_no", columnList = "bookingScheduleNo DESC")
})
public class BookingSchedulePath extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long bookingScheduleNo;

    private LocalTime time;

    private String title;

    private String schedule;

}
