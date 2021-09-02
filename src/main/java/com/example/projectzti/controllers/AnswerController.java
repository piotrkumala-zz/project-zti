package com.example.projectzti.controllers;


import com.example.projectzti.database.services.AnswerService;
import com.example.projectzti.shared.ClientAnswer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST API controller for answer operations
 */
@RestController
public class AnswerController {
    private final AnswerService answerService;

    /**
     * @param answerService Injected service to handle answer operations
     */
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    /**
     * Get survey's answers
     * @param surveyId Survey id
     * @return Answers of requested survey
     */
    @GetMapping("api/answers/{id}")
    public Iterable<ClientAnswer> getSurveyAnswers(@PathVariable("id") UUID surveyId) {
        return answerService.getSurveyAnswers(surveyId).map(ClientAnswer::new).collect(Collectors.toList());
    }


    /**
     * Save answer
     * @param answer Answer to save
     * @return Added answer
     */
    @PostMapping("api/answer")
    public ClientAnswer saveAnswer(@RequestBody @Validated ClientAnswer answer) {
        return new ClientAnswer(this.answerService.insertAnswer(answer));
    }
}
