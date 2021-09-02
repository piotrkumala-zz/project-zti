package com.example.projectzti.shared;

import com.example.projectzti.database.models.AnsweredQuestion;

import java.util.UUID;

/**
 * Client facing Answered Question object
 */
public class ClientAnsweredQuestion {

    private UUID questionId;
    private AnsweredDirection answeredDirection;

    /**
     * Default constructor
     */
    public ClientAnsweredQuestion() {
    }

    /**
     * @param questionId Question's id
     * @param answeredDirection Answer direction
     */
    public ClientAnsweredQuestion(UUID questionId,  AnsweredDirection answeredDirection) {
        this.questionId = questionId;
        this.answeredDirection = answeredDirection;
    }

    /**
     * @param answeredQuestion Answered Question from database
     */
    public ClientAnsweredQuestion(AnsweredQuestion answeredQuestion) {
        this(answeredQuestion.getQuestionId(), answeredQuestion.getAnsweredDirection());
    }

    /**
     * @return Question id
     */
    public UUID getQuestionId() {
        return questionId;
    }

    /**
     * @return Answered Direction
     */
    public AnsweredDirection getAnsweredDirection() {
        return answeredDirection;
    }
}
