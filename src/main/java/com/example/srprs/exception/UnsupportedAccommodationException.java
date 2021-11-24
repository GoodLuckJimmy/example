package com.example.srprs.exception;

import lombok.Getter;

@Getter
public class UnsupportedAccommodationException extends RuntimeException {
    private String message = "지원하지 않는 숙소입니다.";

    public UnsupportedAccommodationException() {
        super();
    }
}
