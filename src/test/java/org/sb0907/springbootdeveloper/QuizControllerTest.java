package org.sb0907.springbootdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.transform.Result;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /////////////////////////////////////////////////////////////////////
    @DisplayName("quiz(): GET /quiz?code=1: 201 Created")
    @Test
    public void getQuiz1() throws Exception {
        // given: 준비
        final String url = "/quiz";

        // when: 실행
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("code", "1"));

        // then: 검증
        result.andExpect(status().isCreated())
                .andExpect(content().string("Created!"));
    }

    @DisplayName("quiz(): GET /quiz?code=2: 202 Bad Request")
    @Test
    public void getQuiz2() throws Exception {
        // given: 준비
        final String url = "/quiz";

        // when: 실행
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("code", "2"));

        // then: 검증
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request!"));
    }

    //////////////////////////////////////////////////////////////////////
    @DisplayName("quiz(): POST /quiz?code=1: 403 Forbidden")
    @Test
    public void postQuiz1() throws Exception {
        // given: 준비
        final String url = "/quiz";

        // when: 실행
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Code(1))));

        // then: 검증
        result.andExpect(status().isForbidden())
                .andExpect(content().string("Forbidden!"));
    }

    @DisplayName("quiz(): POST /quiz?code=13: 200 OK")
    @Test
    public void postQuiz2() throws Exception {
        // given: 준비
        final String url = "/quiz";

        // when: 실행
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Code(13))));

        // then: 검증
        result.andExpect(status().isOk())
                .andExpect(content().string("OK!"));
    }

}