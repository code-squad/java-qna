package codesquad.qna;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    public static final QuestionRepository INSTANCE = new QuestionRepository();
    private List<Question> questions;

    private QuestionRepository() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question findQuestion(int index) {
        return questions.get(index);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
