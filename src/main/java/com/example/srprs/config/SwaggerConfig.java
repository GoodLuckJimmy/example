package com.example.srprs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://srprs-api-dev.example.com"))
                .info(new Info().title("SRPRS API")
                        .description("API 호출 Base uri: \t\n" +
                                "    dev: https://srprs-api-dev.example.com\t\n" +
                                "    stage: https://srprs-api-stage.example.com\t\n" +
                                "    prod: https://srprs-api-prod.example.com\t\n" +
                                "\t\n" +
                                "pc결제 승인요청 응답 url: /payments/inicis/certification/web\t\n" +
                                "모바일 결제 승인 요청 응답 url: /payments/inicis/certification/mobile")
                        .version("v1.0.0"));
    }
}
