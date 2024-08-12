package com.github.Noiseneks.taskManagementSpring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(columnDefinition = "VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'")
    private String name;

    private Long authorId;

    @Column(columnDefinition = "VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'")
    private String description;

    private Long performerId;

    private Status status = Status.BACKLOG;

    private Priority priority = Priority.MEDIUM;

    public enum Status {
        BACKLOG,
        IN_PROCESS,
        COMPLETED
    }

    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    public long getId() {
        return id;
    }

    public Task setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Task setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Priority getPriority() {
        return priority;
    }

    public Task setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Task setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Long getPerformerId() {
        return performerId;
    }

    public Task setPerformerId(Long performerId) {
        this.performerId = performerId;
        return this;
    }
}
