package com.example.projectzti.database.models;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private Date answerDate;

    @ManyToOne(optional = false)
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<AnsweredQuestion> answeredQuestions;

    public Answer() {}

    public Answer(Date answerDate, Survey survey, Set<AnsweredQuestion> answeredQuestions)
    {
        this.answerDate = answerDate;
        this.survey = survey;
        this.answeredQuestions = answeredQuestions;
    }
}
