package com.github.Noiseneks.taskManagementSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TaskManagementApplication.class);
        app.run(args);
    }

}