package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
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
        this.question = getNonEmptyQuestions(request.questions.stream().findFirst().orElseThrow()).stream().map(ClientQuestion::new).collect(Collectors.toSet());
        this.rootQuestion = this.question.stream().filter(x -> Objects.equals(x.getText(), request.questions.stream().findFirst().orElseThrow().questionText)).findFirst().orElseThrow().getId();
        linkQuestions(request);

    }

    private void linkQuestions(CreateSurveyRequest request) {
        var requestQuestionsFlat = getAllQuestions(request.questions.stream().findFirst().orElseThrow());
        for (var question : this.question) {
            requestQuestionsFlat.stream().filter(x -> Objects.equals(x.questionText, question.getText())).findFirst().ifPresent(requestQuestion -> {
                requestQuestion.children.stream().filter(x -> x.isLeft).findFirst().ifPresent(leftChild -> {
                    this.question.stream().filter(x -> Objects.equals(x.getText(), leftChild.questionText)).findFirst().ifPresent(x -> question.setLeft(x.getId()));
                    question.setLeftText(leftChild.answerText);
                });

                requestQuestion.children.stream().filter(x -> !x.isLeft).findFirst().ifPresent(rightChild -> {
                    this.question.stream().filter(x -> Objects.equals(x.getText(), rightChild.questionText)).findFirst().ifPresent(x -> question.setRight(x.getId()));
                    question.setRightText(rightChild.answerText);
                });
            });
        }
    }

    private Set<CreateSurveyQuestion> getNonEmptyQuestions(CreateSurveyQuestion createSurveyQuestion) {
        var originalSet = new HashSet<CreateSurveyQuestion>();
        originalSet.add(createSurveyQuestion);
        getNodeChildren(createSurveyQuestion, originalSet, false);
        return originalSet;
    }

    private Set<CreateSurveyQuestion> getAllQuestions(CreateSurveyQuestion createSurveyQuestion) {
        var originalSet = new HashSet<CreateSurveyQuestion>();
        originalSet.add(createSurveyQuestion);
        getNodeChildren(createSurveyQuestion, originalSet, true);
        return originalSet;
    }


    private void getNodeChildren(CreateSurveyQuestion createSurveyQuestion, Set<CreateSurveyQuestion> set, boolean addNull) {
        if (createSurveyQuestion.children != null && createSurveyQuestion.children.size() > 0) {
            for (var question : createSurveyQuestion.children) {
                if (addNull || question.children != null) {
                    set.add(question);
                }
                getNodeChildren(question, set, addNull);
            }
        }
    }
}
