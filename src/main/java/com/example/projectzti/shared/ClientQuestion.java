package com.example.projectzti.shared;

import com.example.projectzti.database.models.Question;

import java.util.UUID;

public class ClientQuestion {
    private UUID id;
    private String text;
    private UUID right;
    private UUID left;

    public ClientQuestion(UUID creationId, String text, UUID questionRight, UUID questionLeft){
        this.id = creationId;
        this.text = text;
        this.right = questionRight;
        this.left = questionLeft;
    }
    public ClientQuestion(Question question)
    {
        this(question.getExternalId(), question.getQuestionText(), question.getExternalRight(), question.getExternalLeft());
    }

    public ClientQuestion() {}

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
}
