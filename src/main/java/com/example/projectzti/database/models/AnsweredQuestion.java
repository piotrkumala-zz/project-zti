package com.example.projectzti.database.models;

import com.example.projectzti.shared.AnsweredDirection;
import com.example.projectzti.shared.ClientAnsweredQuestion;

import javax.persistence.*;
import java.util.UUID;

/**
 * Answered Question database entity
 */
@Entity
public class AnsweredQuestion extends Metadata {
    @Id
    @GeneratedValue
    private final UUID id = UUID.randomUUID();


    @ManyToOne(optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    private UUID questionId;
    private AnsweredDirection answeredDirection;

    /**
     * Default constructor
     */
    public AnsweredQuestion() {
    }

    /**
     * @param question Answered question info from client
     * @param answer Answer to bind this question to
     */
    public AnsweredQuestion(ClientAnsweredQuestion question, Answer answer) {
        this.questionId = question.getQuestionId();
        this.answeredDirection = question.getAnsweredDirection();
        this.answer = answer;
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
