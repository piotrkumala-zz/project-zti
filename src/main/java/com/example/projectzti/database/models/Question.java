package com.example.projectzti.database.models;

import com.example.projectzti.shared.ClientQuestion;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Question {


    @Id
    @GeneratedValue
    private Long id;

    private UUID externalId;
    private String questionText;
    private UUID externalRight;
    private UUID externalLeft;

    @ManyToOne(optional = false)
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;

    public Question() {

    }

    public Question(UUID externalId, String questionText, UUID externalRight, UUID externalLeft, Survey survey){
        this.externalId = externalId;
        this.questionText = questionText;
        this.externalRight = externalRight;
        this.externalLeft = externalLeft;
        this.survey = survey;
    }

    public Question(ClientQuestion clientQuestion, Survey survey) {
        this(clientQuestion.getId(), clientQuestion.getText(), clientQuestion.getRight(), clientQuestion.getLeft(), survey);
    }

    public String getQuestionText() {
        return questionText;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public UUID getExternalRight() {
        return externalRight;
    }

    public UUID getExternalLeft() {
        return externalLeft;
    }
}
