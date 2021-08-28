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
        var mainQuestion = new CreateSurveyQuestionWithId(request.questions.stream().findFirst().orElseThrow());
        var requestQuestionsFlat = flattenQuestion(mainQuestion);
        this.question = requestQuestionsFlat.stream().filter(x -> x.children != null).map(ClientQuestion::new).collect(Collectors.toSet());
        this.rootQuestion = mainQuestion.id;
        linkQuestions(request, requestQuestionsFlat);

    }

    private void linkQuestions(CreateSurveyRequest request, Set<CreateSurveyQuestionWithId> requestQuestionsFlat) {

        for (var question : this.question) {
            requestQuestionsFlat.stream().filter(x -> Objects.equals(x.id, question.getId())).findFirst().ifPresent(requestQuestion -> {
                requestQuestion.children.stream().filter(x -> x.isLeft).findFirst().ifPresent(leftChild -> {
                    this.question.stream().filter(x -> Objects.equals(x.getId(), leftChild.id)).findFirst().ifPresent(x -> question.setLeft(x.getId()));
                    question.setLeftText(leftChild.answerText);
                });

                requestQuestion.children.stream().filter(x -> !x.isLeft).findFirst().ifPresent(rightChild -> {
                    this.question.stream().filter(x -> Objects.equals(x.getId(), rightChild.id)).findFirst().ifPresent(x -> question.setRight(x.getId()));
                    question.setRightText(rightChild.answerText);
                });
            });
        }
    }

    private Set<CreateSurveyQuestionWithId> flattenQuestion(CreateSurveyQuestionWithId mainQuestion) {
        var set = new HashSet<CreateSurveyQuestionWithId>();
        set.add(mainQuestion);
        getNodeChildren(mainQuestion, set);
        return set;
    }

    private void getNodeChildren(CreateSurveyQuestionWithId createSurveyQuestion, Set<CreateSurveyQuestionWithId> set) {
        if (createSurveyQuestion.children != null && createSurveyQuestion.children.size() > 0) {
            for (var question : createSurveyQuestion.children) {
                set.add(question);
                getNodeChildren(question, set);
            }
        }
    }
}
