package com.example.projectzti.database.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class AnsweredQuestion extends Metadata {
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();


    @ManyToOne(optional = false)
    @JoinColumn(name="answer_id", nullable=false)
    private Answer answer;

    @ManyToOne(optional = false)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    private UUID answerId;
}
