package com.example.projectzti.database.services;

import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.repositories.SurveyRepository;
import com.example.projectzti.shared.ClientSurvey;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Service handling operations on survey
 */
@Service
@Transactional
public class SurveyService {
    private final SurveyRepository repository;

    /**
     * @param repository Injected SpringData survey repository
     */
    public SurveyService(SurveyRepository repository) {
        this.repository = repository;
    }

    /**
     * @return Survey's stream
     */
    public Stream<Survey> getAllSurveys() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false);
    }

    /**
     * @param survey Survey to be added
     * @return Added survey
     */
    public Survey insertSurvey(ClientSurvey survey) {
        Survey entityToSave = new Survey(survey);
        return this.repository.save(entityToSave);
    }

    /**
     * @param id Survey id
     * @return Survey
     */
    public Survey getSurvey(UUID id) {
        return this.repository.findById(id).orElse(null);
    }
}
