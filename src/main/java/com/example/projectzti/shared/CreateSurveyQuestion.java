package com.example.projectzti.shared;

import java.util.Set;

/**
 * Client facing survey question used when creating new surveys
 */
public class CreateSurveyQuestion {
    public String questionText;
    public String answerText;
    public boolean isLeft;
    public Set<CreateSurveyQuestion> children;

    public CreateSurveyQuestion(){}

    public CreateSurveyQuestion(String questionText, String answerText, boolean isLeft) {
        this.questionText = questionText;
        this.answerText = answerText;
        this.isLeft = isLeft;
    }

}