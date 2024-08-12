package com.github.Noiseneks.taskManagementSpring.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private Long id;

    private Long taskId;

    private Long authorId;

    private String text;

    public Long getId() {
        return id;
    }

    public CommentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public CommentDto setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public CommentDto setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentDto setText(String text) {
        this.text = text;
        return this;
    }
}
