package com.example.srprs.common.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApi {

    @Operation(hidden = true)
    @GetMapping("/health-check")
    @ResponseStatus(value = HttpStatus.OK)
    public void healthCheck() {
    }
}
