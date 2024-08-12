package com.github.Noiseneks.taskManagementSpring.core.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

//    @Value("${service.jwt.key}")
    private String key = "!@fsddontforgettoreplacemeplease";

//    @Value("${service.jwt.max-age-in-days}")
    private int maxAgeInDays = 30;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMaxAgeInDays() {
        return maxAgeInDays;
    }

    public void setMaxAgeInDays(int maxAgeInDays) {
        this.maxAgeInDays = maxAgeInDays;
    }
}
