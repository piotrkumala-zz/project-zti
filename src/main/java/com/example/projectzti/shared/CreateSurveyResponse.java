package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.UUID;

public class CreateSurveyResponse {
    public UUID surveyId;

    public CreateSurveyResponse() {
    }

    public CreateSurveyResponse(Survey survey) {
        surveyId = survey.getId();
    }
}
