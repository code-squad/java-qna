package codesquad.question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private static QuestionRepository ourInstance = new QuestionRepository();
    private List<Question> questions = new ArrayList<>();

    public static QuestionRepository getInstance() {
        return ourInstance;
    }

    private QuestionRepository() {
    }

    public void add(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
    }

    public Question get(int index) {
        return questions.get(index - 1);
    }

    public List<Question> getAll() {
        return questions;
    }
}
