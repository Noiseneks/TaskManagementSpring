package com.github.Noiseneks.taskManagementSpring.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("HealthEndpoint")
public class HealthResource implements HealthIndicator {

    @Override
    @GetMapping("/health")
    @Operation(summary = "Checks if the service is up", responses = {@ApiResponse(responseCode = "200", description = "Successful response")})
    public Health health() {
        return Health.up().build();
    }
}
