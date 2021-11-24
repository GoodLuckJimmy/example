package com.example.srprs.member.domain;

import com.example.srprs.common.repository.BaseEntity;
import com.example.srprs.member.dto.MemberInfoChangeRequest;
import com.example.srprs.util.StringAttributeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_member_id", columnList = "id")
})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Convert(converter = StringAttributeConverter.class)
    @Column(unique = true)
    private String id;

    private String password;

    @Convert(converter = StringAttributeConverter.class)
    private String name;

    @Convert(converter = StringAttributeConverter.class)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MemberRoleType role;

    private boolean isFromSocial;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private LoginProvider provider;

    public void changeInfo(MemberInfoChangeRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.phoneNumber = request.getPhoneNumber();
        this.modDate = LocalDateTime.now();
    }
}
