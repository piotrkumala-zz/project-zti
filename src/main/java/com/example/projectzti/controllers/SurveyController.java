package com.example.projectzti.controllers;

import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.services.SurveyService;
import com.example.projectzti.shared.ClientSurvey;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/api/survey")
    public Iterable<ClientSurvey> getSurveys()
    {
        return this.surveyService.getAllSurveys().map(ClientSurvey::new).collect(Collectors.toList());
    }

    @GetMapping("/api/survey/{id}")
    public ClientSurvey getSurvey(@PathVariable(value = "id") UUID id){
        return new ClientSurvey(this.surveyService.getSurvey(id));
    }

    @PostMapping(value = "api/survey")
    public UUID saveSurvey(@RequestBody @Validated ClientSurvey survey)
    {
        return this.surveyService.insertSurvey(survey);
    }
}
