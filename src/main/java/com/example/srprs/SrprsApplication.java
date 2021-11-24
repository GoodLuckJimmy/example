package com.example.srprs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.Security;

@SpringBootApplication
@EnableScheduling
public class SrprsApplication {

    public static void main(String[] args) {
        setJavaProperty();
        SpringApplication.run(SrprsApplication.class, args);
    }

    private static void setJavaProperty() {
        Security.setProperty("networkaddress.cache.ttl" , "0"); // 이니시스 결제시 dns 캐쉬 꺼져야 함
    }

}
