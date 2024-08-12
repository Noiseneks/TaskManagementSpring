package com.github.Noiseneks.taskManagementSpring.controllers.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthResource implements HealthIndicator {

    @Override
    @GetMapping("/health")
    @Operation(summary = "Checks if the service is up", responses = {@ApiResponse(responseCode = "200", description = "Successful response")})
    public Health health() {
        return Health.up().build();
    }
}
