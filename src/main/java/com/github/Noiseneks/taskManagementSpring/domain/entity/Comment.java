package com.github.Noiseneks.taskManagementSpring.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private Long authorId;

    private Long taskId;

    @Column(columnDefinition = "VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'")
    private String text;

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Comment setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Comment setTaskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }
}
