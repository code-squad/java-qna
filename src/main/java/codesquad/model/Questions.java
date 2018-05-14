package codesquad.model;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
    }

    public Question getQuestion(String index) throws IllegalArgumentException {
        for (Question question : questions) {
            if (question.isMatch(index)) {
                return question;
            }
        }
        throw new IllegalArgumentException("일치하는 질문이 없습니다.");
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
