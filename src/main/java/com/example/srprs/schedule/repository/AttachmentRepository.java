package com.example.srprs.schedule.repository;

import com.example.srprs.schedule.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByBookingNo(Long bookingNo);
}
