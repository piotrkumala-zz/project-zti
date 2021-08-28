package com.example.projectzti.shared;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateSurveyQuestionWithId extends CreateSurveyQuestion {
    public UUID id;
    public Set<CreateSurveyQuestionWithId> children;


    public CreateSurveyQuestionWithId(CreateSurveyQuestion createSurveyQuestion) {
        super(createSurveyQuestion.questionText, createSurveyQuestion.answerText, createSurveyQuestion.isLeft);
        id = UUID.randomUUID();
        if(createSurveyQuestion.children != null){
            children = createSurveyQuestion.children.stream().map(CreateSurveyQuestionWithId::new).collect(Collectors.toSet());
        }
    }
}
