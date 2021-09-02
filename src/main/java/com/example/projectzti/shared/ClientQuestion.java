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

    /**
     * Constructor setting all field's
     * @param creationId Question id
     * @param text Question text
     * @param questionRight Right question's id
     * @param questionLeft Left question's id
     * @param rightText Right question's text
     * @param leftText Left question's text
     */
    public ClientQuestion(UUID creationId, String text, UUID questionRight, UUID questionLeft, String rightText, String leftText) {
        this.id = creationId;
        this.text = text;
        this.right = questionRight;
        this.left = questionLeft;
        this.rightText = rightText;
        this.leftText = leftText;
    }

    /**
     * @param question Question from database
     */
    public ClientQuestion(Question question) {
        this(question.getId(), question.getQuestionText(), question.getExternalRight(), question.getExternalLeft(), question.getRightText(), question.getLeftText());
    }

    /**
     * Default constructor
     */
    public ClientQuestion() {
    }

    /**
     * @param createSurveyQuestion Question info from clinet adding new survey
     */
    public ClientQuestion(CreateSurveyQuestionWithId createSurveyQuestion) {
        this.text = createSurveyQuestion.questionText;
        this.id = createSurveyQuestion.id;
    }

    /**
     * @return Question text
     */
    public String getText() {
        return text;
    }

    /**
     * @return Right question's id
     */
    public UUID getRight() {
        return right;
    }

    /**
     * @param id Right question's id
     */
    public void setRight(UUID id) {
        this.right = id;
    }

    /**
     * @return Question's id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Left question's id
     */
    public UUID getLeft() {
        return left;
    }

    /**
     * @param id Left question's id
     */
    public void setLeft(UUID id) {
        this.left = id;
    }

    /**
     * @return Right question's text
     */
    public String getRightText() {
        return rightText;
    }

    /**
     * @param text Right question's text
     */
    public void setRightText(String text) {
        this.rightText = text;
    }

    /**
     * @return Left question's text
     */
    public String getLeftText() {
        return leftText;
    }

    /**
     * @param text Left question's text
     */
    public void setLeftText(String text) {
        this.leftText = text;
    }
}
