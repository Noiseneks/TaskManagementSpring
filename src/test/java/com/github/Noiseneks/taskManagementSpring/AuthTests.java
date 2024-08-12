package com.github.Noiseneks.taskManagementSpring;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Noiseneks.taskManagementSpring.domain.LoginResponse;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.LoginUserDto;
import com.github.Noiseneks.taskManagementSpring.domain.dtos.RegisterUserDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(0)
    public void testAccessWhileNotAuthed() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/tasks/create")
                .accept(MediaType.APPLICATION_JSON);


        mvc.perform(requestBuilder)
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    private static String username;
    private static String userPassword;

    @Test
    @Order(1)
    public void registerNewUserTest() throws Exception {
        username = getRandomString(5, 8);
        userPassword = getRandomString(8, 16);

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

        String jwtToken = loginResponse.getToken();
        assertNotNull(jwtToken);
    }
    
    @Test
    @Order(2)
    public void testLogin() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(username);
        loginUserDto.setPassword(userPassword);

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/auth/login")
                .content(objectMapper.writeValueAsString(loginUserDto))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mvc.perform(requestBuilder2).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        LoginResponse loginResponse = objectMapper.readValue(response.getContentAsString(), LoginResponse.class);
        assertNotNull(loginResponse);

        String jwtToken = loginResponse.getToken();
        assertNotNull(jwtToken);
    }


    private static String getRandomString(int minLength, int maxLength) {
        return UUID.randomUUID().toString().substring(0, minLength + (int) (Math.random() * (maxLength - minLength)));
    }
}
