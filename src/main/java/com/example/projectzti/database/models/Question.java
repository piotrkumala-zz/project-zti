package com.example.projectzti.database.models;

import com.example.projectzti.shared.ClientQuestion;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Question extends Metadata {


    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();
    private String questionText;
    private UUID externalRight;
    private UUID externalLeft;
    private String leftText;
    private String rightText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    public Question() {

    }

    public Question(UUID id, String questionText, UUID externalRight, UUID externalLeft, String leftText, String rightText, Survey survey) {
        this.id = id;
        this.questionText = questionText;
        this.externalRight = externalRight;
        this.externalLeft = externalLeft;
        this.leftText = leftText;
        this.rightText = rightText;
        this.survey = survey;
    }

    public Question(ClientQuestion clientQuestion, Survey survey) {
        this(clientQuestion.getId(), clientQuestion.getText(), clientQuestion.getRight(), clientQuestion.getLeft(), clientQuestion.getLeftText(), clientQuestion.getRightText(), survey);
    }

    public String getQuestionText() {
        return questionText;
    }

    public UUID getExternalRight() {
        return externalRight;
    }

    public UUID getExternalLeft() {
        return externalLeft;
    }

    public UUID getId() {
        return id;
    }

    public String getRightText() {
        return rightText;
    }

    public String getLeftText() {
        return leftText;
    }
}
