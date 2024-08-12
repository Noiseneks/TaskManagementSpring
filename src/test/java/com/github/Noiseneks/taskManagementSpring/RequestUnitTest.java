package com.github.Noiseneks.taskManagementSpring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestUnitTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHealthUp() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/health")
                .accept(MediaType.APPLICATION_JSON);

        String jsonExample = "{\"status\":\"UP\"}";

        if (true) {
            throw new RuntimeException(String.valueOf(content()));
        }

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(jsonExample));
    }

}
