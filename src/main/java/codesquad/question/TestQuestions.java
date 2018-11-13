package codesquad.question;

import java.util.ArrayList;
import java.util.List;

public class TestQuestions {

    public static List<Question> addQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(0, "brad", "질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-01-12 13:12"));
        questions.add(new Question(1, "david", "질문이 질문이 있습니다", "안녕하세요. 모르는게 너무 많아요.", "2018-02-12 18:12"));
        questions.add(new Question(2, "장그래", "코드스쿼드 안녕하세요", "안녕하세요. 하하하하하. 모르는게 너무 많아요.", "2018-11-12 9:12"));
        questions.add(new Question(3, "장보고", "바다 사나이입니다", "안녕하세요. 하하하하하. 배 밖에 모르는게 너무 많아요.", "2018-11-12 9:12"));
        return questions;
    }
}
