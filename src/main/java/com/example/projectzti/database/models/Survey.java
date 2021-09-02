package com.example.projectzti.database.models;


import com.example.projectzti.shared.ClientSurvey;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Survey database entity
 */
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

    /**
     * @param survey Survey info from the client
     */
    public Survey(ClientSurvey survey) {
        this.question = survey.question.stream().map(x -> new Question(x, this)).collect(Collectors.toSet());
        this.title = survey.title;
        this.description = survey.description;
        this.rootQuestion = survey.rootQuestion;
    }

    /**
     * Default constructor
     */
    public Survey() {
    }

    /**
     * @return Survey title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return Survey description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return Root question id
     */
    public UUID getRootQuestion() {
        return this.rootQuestion;
    }

    /**
     * @return Survey id
     */
    public UUID getId() {
        return id;
    }
}
