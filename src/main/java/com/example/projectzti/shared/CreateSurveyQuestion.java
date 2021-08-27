package com.example.projectzti.shared;

import java.util.Set;

public class CreateSurveyQuestion {
    public String questionText;
    public String answerText;
    public boolean isLeft;
    public Set<CreateSurveyQuestion> children;
}
