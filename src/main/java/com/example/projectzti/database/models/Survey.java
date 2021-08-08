package com.example.projectzti.database.models;


import com.example.projectzti.shared.ClientSurvey;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Survey extends Metadata {
    @Id
    @GeneratedValue
    private final UUID id = UUID.randomUUID();
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Question> question;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Answer> answers;
    @Lob
    private String description;
    private String title;
    private UUID rootQuestion;

    public Survey(ClientSurvey survey) {
        this.question = survey.question.stream().map(x -> new Question(x, this)).collect(Collectors.toSet());
        this.title = survey.title;
        this.description = survey.description;
        this.rootQuestion = survey.rootQuestion;
    }

    public Survey() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public UUID getRootQuestion() {
        return this.rootQuestion;
    }

    public UUID getId() {
        return id;
    }
}
