package com.example.srprs.schedule.domain;

import com.example.srprs.common.repository.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_attachment_booking_no", columnList = "bookingNo DESC")
})
public class Attachment extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private Long bookingNo;

    @ElementCollection
    @CollectionTable( name = "attachment_url")
    @Column(length = 300)
    private List<String> attachmentUrl;

    public static Attachment ofEmptyAttachment() {
        return Attachment.builder()
                .no(0L)
                .bookingNo(0L)
                .attachmentUrl(Collections.emptyList())
                .build();
    }

}
