package com.example.projectzti.shared;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Client facing survey question with added id field
 */
public class CreateSurveyQuestionWithId extends CreateSurveyQuestion {
    public final UUID id;
    public Set<CreateSurveyQuestionWithId> children;


    /**
     * This constructor recursively converts all question's children to CreateSurveyQuestionWithId type and gives them ids
     * @param createSurveyQuestion Create survey question without id
     */
    public CreateSurveyQuestionWithId(CreateSurveyQuestion createSurveyQuestion) {
        super(createSurveyQuestion.questionText, createSurveyQuestion.answerText, createSurveyQuestion.isLeft);
        id = UUID.randomUUID();
        if(createSurveyQuestion.children != null){
            children = createSurveyQuestion.children.stream().map(CreateSurveyQuestionWithId::new).collect(Collectors.toSet());
        }
    }
}
