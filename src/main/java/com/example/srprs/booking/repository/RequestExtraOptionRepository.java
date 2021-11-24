package com.example.srprs.booking.repository;

import com.example.srprs.booking.domain.RequestExtraOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestExtraOptionRepository extends JpaRepository<RequestExtraOption, Long>  {
    List<RequestExtraOption> findByBookingNo(Long bookingId);
}
