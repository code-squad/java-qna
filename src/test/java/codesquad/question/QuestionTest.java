package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    User loginUser;
    User otherUser;
    Question question;
    Answer answer1;
    Answer answer2;
    List<Answer> answers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        loginUser = new User();
        otherUser = new User();
        question = new Question();
        answer1 = new Answer();
        answer2 = new Answer();
    }

    @Test
    public void 질문_삭제_답변X() {
        question.setAnswers(answers);
        question.delete(loginUser);
        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 질문_삭제_내_답변만_포함() {
        loginUser.setPId(1);
        question.setWriter(loginUser);
        answer1.setWriter(loginUser);

        answers.add(answer1);
        question.setAnswers(answers);
        question.delete(loginUser);
        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 질문_삭제_다른사람_답변_포함() {
        loginUser.setPId(1);
        otherUser.setPId(2);
        question.setWriter(loginUser);
        answer1.setWriter(loginUser);
        answer2.setWriter(otherUser);

        answers.add(answer1);
        answers.add(answer2);
        question.setAnswers(answers);
        question.delete(loginUser);
        assertThat(question.isDeleted()).isEqualTo(false);
    }
}