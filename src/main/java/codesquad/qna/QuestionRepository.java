package codesquad.qna;

import java.util.ArrayList;
import java.util.List;

// 싱글톤 패턴
// 아래는 DCL(Double-checking Locking) 사용
public class QuestionRepository {
    private volatile static QuestionRepository questionRepository;
    private List<Question> questions;

    private QuestionRepository() {
        questions = new ArrayList<>();
    }

    public static QuestionRepository getQuestionRepository() {
        if (questionRepository == null) {
            synchronized (QuestionRepository.class) { // 생성 안됬을 때만 동기화
                if (questionRepository == null) {
                    questionRepository = new QuestionRepository();
                }
            }
        }
        return questionRepository;
    }

    public void create(Question question){
        question.setId(questions.size() + 1);
        questions.add(question);
    }

    public Question checkSameId(int index) {
        return questions.stream()
                .filter(q -> q.getId() == index)
                .findFirst()
                .orElse(null);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
