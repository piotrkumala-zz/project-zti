package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.*;
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

    public ClientSurvey(CreateSurveyRequest request) {
        this.title = request.title;
        this.description = request.description;
        Set<ClientQuestion> set = new HashSet<>();
        for (CreateSurveyQuestion createSurveyQuestion : request.questions) {
            ClientQuestion clientQuestion = new ClientQuestion(createSurveyQuestion);
            set.addAll(getAllChildren(createSurveyQuestion));
            set.add(clientQuestion);
            this.rootQuestion = clientQuestion.getId();
        }
        this.question = set;

        for (var requestQuestion : request.questions) {
            if (requestQuestion.children.size() > 0) {
                var question = this.question.stream().filter(x -> Objects.equals(x.getText(), requestQuestion.questionText)).findFirst().orElseThrow();
                    var leftChild = (new ArrayList<>(requestQuestion.children)).get(1);
                    this.question.stream().filter(x -> Objects.equals(x.getText(), leftChild.questionText)).findFirst().ifPresent(x -> question.setLeft(x.getId()));
                    question.setLeftText(leftChild.answerText);
                if (requestQuestion.children.size() == 2) {
                    var rightChild = requestQuestion.children.stream().findFirst().orElseThrow();
                    this.question.stream().filter(x -> Objects.equals(x.getText(), rightChild.questionText)).findFirst().ifPresent(x -> x.setRight(x.getId()));
                    question.setRightText(rightChild.answerText);
                }
            }
        }

    }

    private Set<ClientQuestion> getAllChildren(CreateSurveyQuestion createSurveyQuestion) {
        var originalSet = new HashSet<ClientQuestion>();
        getNodeChildren(createSurveyQuestion, originalSet);
        return originalSet;
    }

    private void getNodeChildren(CreateSurveyQuestion createSurveyQuestion, Set<ClientQuestion> set) {
        if (createSurveyQuestion.children != null && createSurveyQuestion.children.size() > 0) {
            for (var question : createSurveyQuestion.children) {
                if(question.children != null){
                    set.add(new ClientQuestion(question));
                }
                getNodeChildren(question, set);
            }
        }
    }
}
