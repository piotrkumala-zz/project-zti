package com.example.projectzti.shared;

import com.example.projectzti.database.models.Answer;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Client facing Answer object
 */
public class ClientAnswer {

    private Set<ClientAnsweredQuestion> answeredQuestions;
    private UUID surveyId;

    /**
     * Default constructor
     */
    public ClientAnswer() {
    }

    /**
     * @param questions Answered questions infos from the client
     * @param surveyId Survey id
     */
    public ClientAnswer(Set<ClientAnsweredQuestion> questions, UUID surveyId) {
        this(questions);
        this.surveyId = surveyId;
    }

    /**
     * @param questions Answered questions infos from the client
     */
    public ClientAnswer(Set<ClientAnsweredQuestion> questions) {
        this.answeredQuestions = questions;
    }

    /**
     * @param answer Database saved answer
     */
    public ClientAnswer(Answer answer) {
        this(answer.answeredQuestions.stream().map(ClientAnsweredQuestion::new).collect(Collectors.toSet()), answer.getSurveyId());
    }

    /**
     * @return Answered questions info
     */
    public Set<ClientAnsweredQuestion> getAnsweredQuestions() {
        return answeredQuestions;
    }

    /**
     * @return Survey's id
     */
    public UUID getSurveyId() {
        return surveyId;
    }
}
