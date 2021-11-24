package com.example.srprs.booking.domain;

import com.example.srprs.booking.dto.RequestOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(indexes = {
        @Index(name = "idx_req_ext_option_booking_no", columnList = "bookingNo DESC")
})
public class RequestExtraOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private RequestOptionType optionType;

    private BigDecimal optionPrice;

    private Long bookingNo;
}
