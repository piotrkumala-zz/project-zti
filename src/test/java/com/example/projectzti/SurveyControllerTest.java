package com.example.projectzti;

import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.repositories.SurveyRepository;
import com.example.projectzti.shared.ClientSurvey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class SurveyControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private SurveyRepository repository;

    @Test
    void shouldGetAllSurveys() throws Exception {
        repository.deleteAll();
        repository.save(new Survey());
        repository.save(new Survey());
        var result = "[{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null},{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null}]";

        this.mockMvc
                .perform(get("/api/survey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void shouldGetOneSurvey() throws Exception {
        repository.deleteAll();
        var entity = repository.save(new Survey());
        var result = "{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null}";


        this.mockMvc
                .perform(get(String.format("/api/survey/%s", entity.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

}
