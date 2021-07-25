package com.example.projectzti.database.services;

import com.example.projectzti.database.models.Answer;
import com.example.projectzti.database.repositories.AnswerRepository;
import com.example.projectzti.shared.ClientAnswer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository repository;
    private final SurveyService surveyService;

    public AnswerService(AnswerRepository repository, SurveyService surveyService) {
        this.repository = repository;
        this.surveyService = surveyService;
    }

    public Stream<Answer> getSurveyAnswers(UUID surveyId) {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false).filter(x -> x.getSurveyId().equals(surveyId));
    }

    public Answer insertAnswer(ClientAnswer answer) {
        var survey = surveyService.getSurvey(answer.getSurveyId());
        var entityToSave = new Answer(survey, answer);
        return this.repository.save(entityToSave);
    }
}
