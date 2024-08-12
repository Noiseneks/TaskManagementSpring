package com.github.Noiseneks.taskManagementSpring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Noiseneks.taskManagementSpring.domain.LoginResponse;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.CommentDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.IdDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.TaskDto;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Comment;
import com.github.Noiseneks.taskManagementSpring.domain.entity.Task;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TasksTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static volatile String jwtToken;

    @Test
    @Order(1)
    public void registerNewAccount() throws Exception {
        String username = getRandomString(5, 8);
        String userPassword = getRandomString(8, 16);

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail(username);
        registerUserDto.setPassword(userPassword);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/signup")
                .content(objectMapper.writeValueAsString(registerUserDto))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        LoginResponse loginResponse = objectMapper.readValue(response.getContentAsString(), LoginResponse.class);
        assertNotNull(loginResponse);

        userId = loginResponse.getUserId();
        assertNotEquals(0, userId);

        jwtToken = loginResponse.getToken();
        assertNotNull(jwtToken);
    }

    private static volatile long userId;

    @Test
    @Order(2)
    public void addTaskTest() throws Exception {

        MockHttpServletResponse response = null;

        for (int i = 0; i < 3; ++i) {
            String taskName = "Сделай " + getRandomString(10, 16);
            String description = "Ну там вот это вот " + getRandomString(16, 32);

            TaskDto taskDto = new TaskDto();
            taskDto.setName(taskName);
            taskDto.setDescription(description);
            taskDto.setPriority("HIGH");

            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post("/tasks/createTask")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .content(objectMapper.writeValueAsString(taskDto))
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
            response = mvcResult.getResponse();
        }

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        Task task = objectMapper.readValue(response.getContentAsString(), Task.class);
        assertNotNull(task);

        taskId = task.getId();
    }

    private static volatile long taskId;

    @Test
    @Order(3)
    public void editTaskTest() throws Exception {

        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskId);
        taskDto.setStatus("COMPLETED");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/tasks/editTask")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(taskDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        Task task = objectMapper.readValue(response.getContentAsString(), Task.class);
        assertNotNull(task);
    }

    @Test
    @Order(4)
    public void addCommentToTaskTest() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Продолжай в том же духе! " + getRandomString(10, 20));
        commentDto.setTaskId(taskId);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/tasks/addComment")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(commentDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        Comment comment = objectMapper.readValue(response.getContentAsString(), Comment.class);
        assertNotNull(comment);
        commentId = comment.getId();
    }

    private static long commentId;

    @Test
    @Order(5)
    public void deleteCommentToTaskTest() throws Exception {
        IdDto idDto = new IdDto();
        idDto.setId(commentId);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/tasks/deleteComment")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(idDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @Order(6)
    public void getListAuthoredByIdTest() throws Exception {
        IdDto idDto = new IdDto();
        idDto.setId(userId);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tasks/getByAuthorId")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .content(objectMapper.writeValueAsString(idDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        List<Task> tasks = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
        });
        assertEquals(3, tasks.size());
    }


    private static String getRandomString(int minLength, int maxLength) {
        return UUID.randomUUID().toString().substring(0, minLength + (int) (Math.random() * (maxLength - minLength)));
    }
}
