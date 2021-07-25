package com.example.projectzti.shared;

import com.example.projectzti.database.models.Answer;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientAnswer {

    private Set<ClientAnsweredQuestion> answeredQuestions;
    private UUID surveyId;

    public ClientAnswer() {
    }

    public ClientAnswer(Set<ClientAnsweredQuestion> questions, UUID surveyId) {
        this.answeredQuestions = questions;
        this.surveyId = surveyId;
    }

    public ClientAnswer(Answer answer) {
        this(answer.answeredQuestions.stream().map(ClientAnsweredQuestion::new).collect(Collectors.toSet()), answer.getSurveyId());
    }

    public Set<ClientAnsweredQuestion> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public UUID getSurveyId() {
        return surveyId;
    }
}
