package com.example.srprs.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class KaKaoRestTest {
    private RestTemplate restTemplate;
    private HttpEntity headers;
    private static final String KAKAO_URL = "https://kapi.kakao.com";
    private static final String ACCESS_TOKEN = "t0kGSDIQm1yqXco5-iQkdju-1Hr5q9lM-99Y3Qo9dVoAAAF7oKXqow";

    @BeforeEach
    void setup() {
        this.restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", "Bearer " + ACCESS_TOKEN);
        header.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        this.headers = new HttpEntity(header);
    }

    @Test
    @Disabled("kakao access token 필요")
    void getUserInfo() {
        KakaoProfile kakao = restTemplate.exchange(KAKAO_URL + "/v2/user/me", HttpMethod.GET, headers, KakaoProfile.class).getBody();
        System.out.println(kakao.getId());
        System.out.println(kakao.getAccount().getEmail());
        assertThat(kakao.getId()).isEqualTo(1881838701L);
        assertThat(kakao.getAccount().getEmail()).isEqualTo("example@kakao.com");
    }
}

@Data
@NoArgsConstructor
class KakaoProfile {
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount account;
}

@Data
@NoArgsConstructor
class KakaoAccount {
    private String email;
}