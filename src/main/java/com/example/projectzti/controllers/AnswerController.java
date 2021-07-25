package com.example.projectzti.controllers;


import com.example.projectzti.database.services.AnswerService;
import com.example.projectzti.shared.ClientAnswer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @GetMapping("api/answers/{id}")
    public Iterable<ClientAnswer> getSurveyAnswers(@PathVariable("id") UUID surveyId) {
        return answerService.getSurveyAnswers(surveyId).map(ClientAnswer::new).collect(Collectors.toList());
    }


    @PostMapping("api/answer")
    public ClientAnswer saveAnswer(@RequestBody @Validated ClientAnswer answer) {
        return new ClientAnswer(this.answerService.insertAnswer(answer));
    }
}
