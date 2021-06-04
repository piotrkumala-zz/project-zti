package com.example.projectzti.database.models;

import javax.persistence.*;

@Entity
public class AnsweredQuestion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="answer_id", nullable=false)
    private Answer answer;
}
