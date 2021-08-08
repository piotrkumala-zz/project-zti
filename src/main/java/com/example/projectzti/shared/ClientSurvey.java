package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientSurvey {

    public Set<ClientQuestion> question;
    public String title;
    public String description;
    public UUID rootQuestion;

    public ClientSurvey(Survey survey) {
        this.question = survey.question.stream().map(ClientQuestion::new).collect(Collectors.toSet());
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.rootQuestion = survey.getRootQuestion();
    }

    public ClientSurvey() {
    }
}
