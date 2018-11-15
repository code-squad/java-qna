package codesquad.question;

import java.util.ArrayList;
import java.util.List;

//todo 다시 생각해보기... 싱글톤 개념과 활용 등...
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

    public static Question findMatchQuestion(int index) {
        return questions.get(index - 1);
    }
}
//가장 기본적인 Eager initialization 싱글톤 패턴
//ref : https://blog.seotory.com/post/2016/03/java-singleton-pattern