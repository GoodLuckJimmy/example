package com.example.srprs.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SlackMessage {
    private String username = "srprs";
    private String text;

    @Builder
    public SlackMessage(String text) {
        this.text = text;
    }
}
