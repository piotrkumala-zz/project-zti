package com.example.projectzti;

import com.example.projectzti.database.models.Answer;
import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.repositories.AnswerRepository;
import com.example.projectzti.database.repositories.SurveyRepository;
import com.example.projectzti.shared.ClientAnswer;
import com.example.projectzti.shared.ClientAnsweredQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnswerIntegrationTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    void shouldGetSurveyAnswers() throws Exception {
        answerRepository.deleteAll();
        var survey = surveyRepository.save(new Survey());
        var answers = new HashSet<ClientAnsweredQuestion>();
        answers.add(new ClientAnsweredQuestion());
        answers.add(new ClientAnsweredQuestion());
        answerRepository.save(new Answer(survey, new ClientAnswer(answers)));
        answerRepository.save(new Answer(survey, new ClientAnswer(answers)));
        var result = ""
                .concat(String.format(
                        "[{\"answeredQuestions\":[{\"questionId\":null,\"answeredDirection\":null},{\"questionId\":null,\"answeredDirection\":null}], \"surveyId\":\"%s\"},",
                        survey.getId()))
                .concat(String.format(
                        "{\"answeredQuestions\":[{\"questionId\":null,\"answeredDirection\":null},{\"questionId\":null,\"answeredDirection\":null}], \"surveyId\":\"%s\"}],",
                        survey.getId()));

        this.mockMvc
                .perform(get(String.format("/api/answers/%s", survey.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void shouldSaveAnswer() throws Exception {
        answerRepository.deleteAll();
        var survey = surveyRepository.save(new Survey());
        var answers = new HashSet<ClientAnsweredQuestion>();
        answers.add(new ClientAnsweredQuestion());
        answers.add(new ClientAnsweredQuestion());
        var request = new Answer(survey, new ClientAnswer(answers));
        this.mockMvc
                .perform(post("/api/answer")
                        .content(this.mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(this.mapper.writeValueAsString(request)));
    }
}
