package com.example.projectzti.database.models;


import com.example.projectzti.shared.ClientSurvey;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Survey {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Timestamp createdDate;

    @Lob
    private String description;

    private String title;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Question> question;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Answer> answers;

    public Survey(ClientSurvey survey) {
        this.question = survey.question.stream().map(x -> new Question(x, this)).collect(Collectors.toSet());
    }

    public Survey() {}

    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
