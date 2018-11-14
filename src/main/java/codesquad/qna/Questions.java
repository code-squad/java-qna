package codesquad.qna;

import codesquad.utils.CalculateDate;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private List<Question> questions = new ArrayList<>();

    private Questions() {}

    static Questions of() {
        return new Questions();
    }

    void add(Question question) {
        question.setTime(CalculateDate.getToday());
        question.setIndex(questions.size() + 1);
        questions.add(question);
    }

    List<Question> getQuestions() {
        return questions;
    }
}
