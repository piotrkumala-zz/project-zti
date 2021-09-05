package com.example.projectzti.controllers;

import com.example.projectzti.database.services.SurveyService;
import com.example.projectzti.shared.ClientSurvey;
import com.example.projectzti.shared.CreateSurveyRequest;
import com.example.projectzti.shared.CreateSurveyResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST API controller for survey operations
 */
@RestController
public class SurveyController {
    private final SurveyService surveyService;

    /**
     * @param surveyService Injected service to handle survey operations
     */
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * Get all surveys
     * @return All surveys info
     */
    @GetMapping("/api/survey")
    public Iterable<ClientSurvey> getSurveys() {
        return this.surveyService.getAllSurveys().map(ClientSurvey::new).collect(Collectors.toList());
    }

    /**
     * Get specific survey info
     * @param id Survey id
     * @return Survey info
     */
    @GetMapping("/api/survey/{id}")
    public ClientSurvey getSurvey(@PathVariable(value = "id") UUID id) {
        return new ClientSurvey(this.surveyService.getSurvey(id));
    }

    /**
     * Create new survey
     * @param survey Survey to be saved
     * @return Added survey
     */
    @PostMapping(value = "api/survey")
    public CreateSurveyResponse saveSurvey(@RequestBody @Validated CreateSurveyRequest survey) throws Throwable {
        return new CreateSurveyResponse(this.surveyService.insertSurvey(new ClientSurvey(survey)));
    }
}
