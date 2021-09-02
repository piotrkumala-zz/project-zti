package com.example.projectzti.unit;


import com.example.projectzti.database.models.Survey;
import com.example.projectzti.shared.ClientQuestion;
import com.example.projectzti.shared.ClientSurvey;
import com.example.projectzti.shared.CreateSurveyQuestion;
import com.example.projectzti.shared.CreateSurveyRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests of survey conversion
 */
public class SurveyConversionTest {

    @Test
    void shouldConvertClientWithOneQuestionToEntity() {
        var request = new CreateSurveyRequest();
        request.title = "test title";
        request.description = "test description";
        request.questions = new HashSet<>();
        var question = new CreateSurveyQuestion("question", null, false);
        question.children = new HashSet<>();
        question.children.add(new CreateSurveyQuestion(null, "left", true));
        question.children.add(new CreateSurveyQuestion(null, "right", false));
        request.questions.add(question);

        var result = new ClientSurvey(request);
        var resultQuestions = new ArrayList<>(result.question);
        assertThat(result)
                .isNotNull()
                .extracting(x -> x.title, x -> x.description, x -> x.rootQuestion)
                .doesNotContainNull()
                .containsExactly(request.title, request.description, resultQuestions.get(0).getId());

        assertThat(resultQuestions)
                .isNotNull()
                .hasSize(1);

        assertThat(resultQuestions.get(0))
                .isNotNull()
                .extracting(
                        ClientQuestion::getText,
                        ClientQuestion::getLeftText,
                        ClientQuestion::getLeft,
                        ClientQuestion::getRightText,
                        ClientQuestion::getRight)
                .containsExactly("question", "left", null, "right", null);
    }

    @Test
    void shouldConvertClientToEntityAndBack() {
        var request = new CreateSurveyRequest();
        request.title = "test title";
        request.description = "test description";
        request.questions = new HashSet<>();
        var question = new CreateSurveyQuestion("question", null, false);
        question.children = new HashSet<>();
        question.children.add(new CreateSurveyQuestion(null, "left", true));
        question.children.add(new CreateSurveyQuestion(null, "right", false));
        request.questions.add(question);

        var clientSurvey = new ClientSurvey(request);
        var entity = new Survey(clientSurvey);

        assertThat(new ClientSurvey(entity)).isNotNull().usingRecursiveComparison().isEqualTo(clientSurvey);
    }

    @Test
    void shouldConvertClientWithMultipleQuestionsToEntity() {
        var request = new CreateSurveyRequest();
        request.title = "test title";
        request.description = "test description";
        request.questions = new HashSet<>();
        var question = new CreateSurveyQuestion("question", null, false);
        var set = new HashSet<CreateSurveyQuestion>();
        set.add(new CreateSurveyQuestion(null, "left", true));
        set.add(new CreateSurveyQuestion(null, "right", false));
        var leftChild = new CreateSurveyQuestion("left question", "left", true);
        leftChild.children = set;
        var rightChild = new CreateSurveyQuestion("right question", "right", false);
        rightChild.children = set;

        question.children = new HashSet<>();
        question.children.add(leftChild);
        question.children.add(rightChild);
        request.questions.add(question);

        var result = new ClientSurvey(request);
        var resultQuestions = new ArrayList<>(result.question);
        assertThat(result)
                .isNotNull()
                .extracting(x -> x.title, x -> x.description, x -> x.rootQuestion)
                .doesNotContainNull()
                .containsExactly(
                        request.title,
                        request.description,
                        resultQuestions.stream().filter(
                                x-> Objects.equals(x.getText(), new ArrayList<>(request.questions).get(0).questionText))
                                .findFirst()
                                .orElseThrow()
                                .getId());

        assertThat(resultQuestions)
                .isNotNull()
                .hasSize(3);

        assertThat(resultQuestions.stream().filter(x-> x.getId() == result.rootQuestion).findFirst().orElseThrow())
                .isNotNull()
                .extracting(
                        ClientQuestion::getText,
                        ClientQuestion::getLeftText,
                        ClientQuestion::getLeft,
                        ClientQuestion::getRightText,
                        ClientQuestion::getRight)
                .containsExactly(
                        "question",
                        "left",
                        resultQuestions.stream().filter(x -> Objects.equals(x.getText(), "left question")).findFirst().orElseThrow().getId(),
                        "right",
                        resultQuestions.stream().filter(x -> Objects.equals(x.getText(), "right question")).findFirst().orElseThrow().getId()
                );
    }
}
