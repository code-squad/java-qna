package codesquad.question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private static QuestionRepository questionRepository = new QuestionRepository();
    private static List<Question> questions = new ArrayList<>();

    private QuestionRepository() { }

    public static QuestionRepository getQuestionRepository() {
        if(questionRepository == null) {
            questionRepository = new QuestionRepository();
        }
        return questionRepository;
    }

    static void addQuestion(Question question) {
        questions.add(question);
    }

    public static List<Question> getQuestions() {
        return questions;
    }
}
//가장 기본적인 Eager initialization 싱글톤 패턴
//ref : https://blog.seotory.com/post/2016/03/java-singleton-pattern