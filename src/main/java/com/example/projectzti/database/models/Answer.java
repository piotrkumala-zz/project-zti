package com.example.projectzti.database.models;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
public class Answer extends Metadata{
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    @ManyToOne(optional = false)
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AnsweredQuestion> answeredQuestions;

    public Answer() {}

    public Answer(Survey survey, Set<AnsweredQuestion> answeredQuestions)
    {
        this.survey = survey;
        this.answeredQuestions = answeredQuestions;
    }
}
