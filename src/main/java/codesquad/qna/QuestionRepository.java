package codesquad.qna;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private static QuestionRepository repository;
    private List<Question> questions = new ArrayList<>();

    private QuestionRepository() {

    }

    public static QuestionRepository getInstance() {
        if (repository == null) {
            repository = new QuestionRepository();
        }
        return repository;
    }

    public void addQuestion(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
