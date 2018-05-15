package codesquad.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Questions {
    private ArrayList<Question> questions = new ArrayList<>();

    public Questions() {

    }

    public void add(Question question) {
        question.setId(questions.size() + 1);
        questions.add(question);
        Collections.sort(questions);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Optional<Question> findById(int id) {
        return Optional.of(questions.get(id - 1));
    }
}
