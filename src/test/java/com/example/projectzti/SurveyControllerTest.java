package com.example.projectzti;

import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.repositories.SurveyRepository;
import com.example.projectzti.shared.ClientSurvey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SurveyRepository repository;

    @Test
    void shouldGetAllSurveys() throws Exception {
        var mockSurveys = new ArrayList<Survey>();
        mockSurveys.add(new Survey());
        mockSurveys.add(new Survey());
        when(repository.findAll()).thenReturn(mockSurveys);

        this.mockMvc
                .perform(get("/api/survey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(mockSurveys.stream().map(ClientSurvey::new).collect(Collectors.toList()))));
    }

    @Test
    void shouldGetOneSurvey() throws Exception {
        when(repository.findById(any())).thenReturn(java.util.Optional.of(new Survey()));

        this.mockMvc
                .perform(get(String.format("/api/survey/%s", UUID.randomUUID())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new ClientSurvey())));
    }

}
