package com.example.projectzti.shared;

import com.example.projectzti.database.models.Survey;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Client facing Survey object
 */
public class ClientSurvey {

    public Set<ClientQuestion> question;
    public String title;
    public String description;
    public UUID rootQuestion;

    /**
     * @param survey Survey from database
     */
    public ClientSurvey(Survey survey) {
        this.question = survey.question != null ? survey.question.stream().map(ClientQuestion::new).collect(Collectors.toSet()) : null;
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.rootQuestion = survey.getRootQuestion();
    }

    /**
     * Default constructor
     */
    public ClientSurvey() {
    }

    /**
     * @param request Survey info from client creating new survey
     */
    public ClientSurvey(CreateSurveyRequest request) throws Throwable {
        this.title = request.title;
        this.description = request.description;
        CreateSurveyQuestionWithId mainQuestion = new CreateSurveyQuestionWithId(request.questions.stream().findFirst().orElseThrow(Throwable::new));
        Set<CreateSurveyQuestionWithId> requestQuestionsFlat = flattenQuestion(mainQuestion);
        this.question = requestQuestionsFlat.stream().filter(x -> x.children != null).map(ClientQuestion::new).collect(Collectors.toSet());
        this.rootQuestion = mainQuestion.id;
        linkQuestions(requestQuestionsFlat);

    }

    /**
     * Link question's children with uuid to proper db structure
     * @param requestQuestionsFlat Questions from create survey request
     */
    private void linkQuestions(Set<CreateSurveyQuestionWithId> requestQuestionsFlat) {

        for (ClientQuestion question : this.question) {
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

    /**
     * Flattens questions from create survey to 1D array
     * @param mainQuestion New survey's root question
     * @return Flattened questions from create survey request
     */
    private Set<CreateSurveyQuestionWithId> flattenQuestion(CreateSurveyQuestionWithId mainQuestion) {
        HashSet<CreateSurveyQuestionWithId> set = new HashSet<>();
        set.add(mainQuestion);
        getNodeChildren(mainQuestion, set);
        return set;
    }

    /**
     * Recursive method retrieving all node's children
     * @param createSurveyQuestion Question to get children's list
     * @param set Children's list
     */
    private void getNodeChildren(CreateSurveyQuestionWithId createSurveyQuestion, Set<CreateSurveyQuestionWithId> set) {
        if (createSurveyQuestion.children != null && createSurveyQuestion.children.size() > 0) {
            for (CreateSurveyQuestionWithId question : createSurveyQuestion.children) {
                set.add(question);
                getNodeChildren(question, set);
            }
        }
    }
}
