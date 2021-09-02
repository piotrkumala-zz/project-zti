package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.UUID;


/**
 * Client response after creating survey
 */
public class CreateSurveyResponse {
    public UUID surveyId;

    public CreateSurveyResponse() {
    }

    public CreateSurveyResponse(Survey survey) {
        surveyId = survey.getId();
    }
}
