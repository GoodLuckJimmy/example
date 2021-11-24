package com.example.srprs.item.domain;

import com.example.srprs.booking.domain.AccommodationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Setter
@Getter
public class Accommodation {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccommodationType accommodationType;
}
