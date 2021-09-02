package com.example.projectzti.database.models;

import com.example.projectzti.shared.ClientQuestion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Question database entity
 */
@Entity
public class Question extends Metadata {


    @Id
    private UUID id = UUID.randomUUID();
    private String questionText;
    private UUID externalRight;
    private UUID externalLeft;
    private String leftText;
    private String rightText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    /**
     * Default constructor
     */
    public Question() {
    }

    /**
     * Constructor setting all fields values used in other constructors
     * @param id Question id
     * @param questionText Question text
     * @param externalRight Right's question id
     * @param externalLeft Left's question id
     * @param leftText Text describing left question
     * @param rightText Text describing right question
     * @param survey Survey to which this question is bound
     */
    public Question(UUID id, String questionText, UUID externalRight, UUID externalLeft, String leftText, String rightText, Survey survey) {
        this.id = id;
        this.questionText = questionText;
        this.externalRight = externalRight;
        this.externalLeft = externalLeft;
        this.leftText = leftText;
        this.rightText = rightText;
        this.survey = survey;
    }

    /**
     * @param clientQuestion Question info from the client
     * @param survey Survey to which this question is bound
     */
    public Question(ClientQuestion clientQuestion, Survey survey) {
        this(clientQuestion.getId(), clientQuestion.getText(), clientQuestion.getRight(), clientQuestion.getLeft(), clientQuestion.getLeftText(), clientQuestion.getRightText(), survey);
    }

    /**
     * @return Question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * @return Right's question id
     */
    public UUID getExternalRight() {
        return externalRight;
    }

    /**
     * @return Left's question id
     */
    public UUID getExternalLeft() {
        return externalLeft;
    }

    /**
     * @return Question id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Text describing right question
     */
    public String getRightText() {
        return rightText;
    }

    /**
     * @return Text describing left question
     */
    public String getLeftText() {
        return leftText;
    }
}
