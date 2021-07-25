package com.example.projectzti.database.models;


import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

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

    public Answer() {
    }

    public Answer(Survey survey, Set<AnsweredQuestion> answeredQuestions) {
        this.survey = survey;
        this.answeredQuestions = answeredQuestions;
    }
}
