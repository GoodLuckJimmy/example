package com.example.srprs.admin.admin.domain;

import com.example.srprs.member.domain.MemberRoleType;
import com.example.srprs.util.StringAttributeConverter;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_admin_id", columnList = "id")
})
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Convert(converter = StringAttributeConverter.class)
    @Column(unique = true)
    private String id;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MemberRoleType role;
}
