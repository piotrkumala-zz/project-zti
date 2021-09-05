package com.example.projectzti.integration;

import com.example.projectzti.database.models.Question;
import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.repositories.SurveyRepository;
import com.example.projectzti.shared.CreateSurveyQuestion;
import com.example.projectzti.shared.CreateSurveyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of survey operations
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SurveyIntegrationTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SurveyRepository repository;

    @Test
    void shouldGetAllSurveys() throws Exception {
        repository.deleteAll();
        repository.save(new Survey());
        repository.save(new Survey());
        String result = "[{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null},{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null}]";

        this.mockMvc
                .perform(get("/api/survey"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void shouldGetOneSurvey() throws Exception {
        repository.deleteAll();
        Survey entity = repository.save(new Survey());
        String result = "{\"question\":[],\"title\":null,\"description\":null,\"rootQuestion\":null}";


        this.mockMvc
                .perform(get(String.format("/api/survey/%s", entity.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void shouldSaveSurvey() throws Exception {
        CreateSurveyRequest request = new CreateSurveyRequest();
        request.title = "test title";
        request.description = "test description";
        request.questions = new HashSet<>();
        CreateSurveyQuestion question = new CreateSurveyQuestion("question", "answer", false);
        question.children = new HashSet<>();
        question.children.add(new CreateSurveyQuestion(null, "left", true));
        question.children.add(new CreateSurveyQuestion(null, "right", false));
        request.questions.add(question);

        MvcResult result = this.mockMvc.perform(post("/api/survey")
                        .content(this.mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Survey addedSurvey = this.repository.findById(
                UUID.fromString(new JSONObject(result
                        .getResponse()
                        .getContentAsString())
                        .getString("surveyId"))).orElseThrow();

        assertThat(addedSurvey)
                .isNotNull()
                .extracting(Survey::getTitle, Survey::getDescription)
                .doesNotContainNull()
                .containsExactly(request.title, request.description);

        assertThat(addedSurvey.question)
                .isNotNull()
                .hasSize(1);

        assertThat(new ArrayList<>(addedSurvey.question).get(0))
                .isNotNull()
                .extracting(Question::getQuestionText, Question::getLeftText, Question::getRightText)
                .doesNotContainNull()
                .containsExactly(
                        question.questionText,
                        question.children.stream().filter(x -> x.isLeft).findFirst().orElseThrow().answerText,
                        question.children.stream().filter(x -> !x.isLeft).findFirst().orElseThrow().answerText);
    }

}
