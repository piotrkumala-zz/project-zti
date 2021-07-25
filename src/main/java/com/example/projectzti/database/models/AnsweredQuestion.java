package com.example.projectzti.database.models;

import com.example.projectzti.shared.ClientAnsweredQuestion;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class AnsweredQuestion extends Metadata {
    @Id
    @GeneratedValue
    private final UUID id = UUID.randomUUID();


    @ManyToOne(optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    private UUID questionId;
    private UUID answeredId;

    public AnsweredQuestion() {
    }

    public AnsweredQuestion(ClientAnsweredQuestion question, Answer answer, Survey survey) {
        this.answeredId = question.getAnsweredId();
        this.questionId = question.getQuestionId();
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public UUID getAnsweredId() {
        return answeredId;
    }
}
