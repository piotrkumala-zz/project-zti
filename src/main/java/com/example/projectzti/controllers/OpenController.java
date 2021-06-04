package com.example.projectzti.controllers;

import com.example.projectzti.database.models.Survey;
import com.example.projectzti.database.services.SurveyService;
import com.example.projectzti.shared.ClientSurvey;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class OpenController {
    private final SurveyService surveyService;

    public OpenController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/api/survey")
    public Iterable<ClientSurvey> getSurveys()
    {
        return this.surveyService.getAllSurveys().map(ClientSurvey::new).collect(Collectors.toList());
    }

    @PostMapping(value = "api/survey")
    public ResponseEntity saveAnswer(@RequestBody @Validated ClientSurvey survey)
    {
        this.surveyService.insertSurvey(survey);
        return ResponseEntity.ok("");
    }
}
