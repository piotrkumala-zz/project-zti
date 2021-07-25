package com.example.projectzti.controllers;

import com.example.projectzti.database.services.SurveyService;
import com.example.projectzti.shared.ClientSurvey;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/api/survey")
    public Iterable<ClientSurvey> getSurveys() {
        return this.surveyService.getAllSurveys().map(ClientSurvey::new).collect(Collectors.toList());
    }

    @GetMapping("/api/survey/{id}")
    public ClientSurvey getSurvey(@PathVariable(value = "id") UUID id) {
        return new ClientSurvey(this.surveyService.getSurvey(id));
    }

    @PostMapping(value = "api/survey")
    public ClientSurvey saveSurvey(@RequestBody @Validated ClientSurvey survey) {
        return new ClientSurvey(this.surveyService.insertSurvey(survey));
    }
}
