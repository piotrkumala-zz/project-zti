package com.example.projectzti.shared;

import com.example.projectzti.database.models.AnsweredQuestion;

import java.util.UUID;

/**
 * Client facing Answered Question object
 */
public class ClientAnsweredQuestion {

    private UUID questionId;
    private AnsweredDirection answeredDirection;

    public ClientAnsweredQuestion(UUID questionId,  AnsweredDirection answeredDirection) {
        this.questionId = questionId;
        this.answeredDirection = answeredDirection;
    }

    public ClientAnsweredQuestion() {
    }


    public ClientAnsweredQuestion(AnsweredQuestion answeredQuestion) {
        this(answeredQuestion.getQuestionId(), answeredQuestion.getAnsweredDirection());
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public AnsweredDirection getAnsweredDirection() {
        return answeredDirection;
    }
}
