package com.example.projectzti.database.models;


import com.example.projectzti.shared.ClientAnswer;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Answer database entity
 */
@Entity
public class Answer extends Metadata {
    @Id
    @GeneratedValue
    private final UUID id = UUID.randomUUID();
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AnsweredQuestion> answeredQuestions;
    @ManyToOne(optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    /**
     * Default constructor
     */
    public Answer() {
    }

    /**
     * @param survey Survey being answered
     * @param answer Answer info from client
     */
    public Answer(Survey survey, ClientAnswer answer) {
        this.survey = survey;
        this.answeredQuestions = answer.getAnsweredQuestions().stream().map(x -> new AnsweredQuestion(x, this)).collect(Collectors.toSet());
    }

    /**
     * @return Answered survey id
     */
    public UUID getSurveyId() {
        return survey.getId();
    }
}
