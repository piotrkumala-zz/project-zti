package com.example.projectzti.shared;

import com.example.projectzti.database.models.AnsweredQuestion;

import java.util.UUID;

public class ClientAnsweredQuestion {

    private UUID questionId;
    private UUID answeredId;

    public ClientAnsweredQuestion(UUID questionId, UUID answeredID) {
        this.questionId = questionId;
        this.answeredId = answeredID;
    }

    public ClientAnsweredQuestion() {
    }


    public ClientAnsweredQuestion(AnsweredQuestion answeredQuestion) {
        this(answeredQuestion.getQuestionId(), answeredQuestion.getAnsweredId());
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public UUID getAnsweredId() {
        return answeredId;
    }

}
