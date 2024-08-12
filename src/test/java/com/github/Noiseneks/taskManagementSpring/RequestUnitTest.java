package com.github.Noiseneks.taskManagementSpring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        throw new RuntimeException(mvcResult.getResponse().getContentAsString());
    }

}
