package com.github.Noiseneks.taskManagementSpring.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public IdDto setId(Long id) {
        this.id = id;
        return this;
    }
}
