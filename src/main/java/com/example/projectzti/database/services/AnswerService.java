package com.example.projectzti.database.services;

import com.example.projectzti.database.models.Answer;
import com.example.projectzti.database.repositories.AnswerRepository;
import com.example.projectzti.shared.ClientAnswer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Service handling operations on answers
 */
@Service
@Transactional
public class AnswerService {

    private final AnswerRepository repository;
    private final SurveyService surveyService;

    /**
     * @param repository Injected SpringData answer repository
     * @param surveyService Injected service to handle survey operations
     */
    public AnswerService(AnswerRepository repository, SurveyService surveyService) {
        this.repository = repository;
        this.surveyService = surveyService;
    }

    /**
     * @param surveyId Survey id
     * @return Answers stream
     */
    public Stream<Answer> getSurveyAnswers(UUID surveyId) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false).filter(x -> x.getSurveyId().equals(surveyId));
    }

    /**
     * @param answer Answer to add
     * @return Added answer
     */
    public Answer insertAnswer(ClientAnswer answer) {
        var survey = surveyService.getSurvey(answer.getSurveyId());
        var entityToSave = new Answer(survey, answer);
        return this.repository.save(entityToSave);
    }
}
