package com.example.projectzti.shared;

import com.example.projectzti.database.models.Question;

import java.util.UUID;

/**
 * Client facing Question object
 */
public class ClientQuestion {
    private UUID id;
    private String text;
    private UUID right;
    private UUID left;
    private String rightText;
    private String leftText;

    public ClientQuestion(UUID creationId, String text, UUID questionRight, UUID questionLeft, String rightText, String leftText) {
        this.id = creationId;
        this.text = text;
        this.right = questionRight;
        this.left = questionLeft;
        this.rightText = rightText;
        this.leftText = leftText;
    }
    public ClientQuestion(Question question) {
        this(question.getId(), question.getQuestionText(), question.getExternalRight(), question.getExternalLeft(), question.getRightText(), question.getLeftText());
    }

    public ClientQuestion() {
    }

    public ClientQuestion(CreateSurveyQuestionWithId createSurveyQuestion) {
        this.text = createSurveyQuestion.questionText;
        this.id = createSurveyQuestion.id;
    }

    public String getText() {
        return text;
    }

    public UUID getRight() {
        return right;
    }

    public UUID getId() {
        return id;
    }

    public UUID getLeft() {
        return left;
    }

    public String getRightText() {
        return rightText;
    }

    public String getLeftText() {
        return leftText;
    }

    public void setRight(UUID id) {
        this.right = id;
    }

    public void setRightText(String text){
        this.rightText = text;
    }

    public void setLeft(UUID id) {
        this.left = id;
    }

    public void setLeftText(String text){
        this.leftText = text;
    }
}
