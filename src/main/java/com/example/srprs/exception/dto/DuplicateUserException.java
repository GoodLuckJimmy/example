package com.example.srprs.exception.dto;


import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {

    private String message = "중복된 유저는 등록 불가";

    public DuplicateUserException() {
        super();
    }
}
