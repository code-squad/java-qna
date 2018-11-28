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
    User other;
    Question question;
    Answer answer1;
    Answer answer2;
    Answer answer3;
    List<Answer> answers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        loginUser = new User();
        other = new User();
        question = new Question();
        answer1 = new Answer();
        answer2 = new Answer();
        answer3 = new Answer();

        loginUser.setPId(1);
        other.setPId(2);
        question.setWriter(loginUser);
        answer1.setWriter(loginUser);
        answer2.setWriter(loginUser);
        answer3.setWriter(other);
    }

    @Test
    public void 질문_삭제_답변X() {
        question.setAnswers(answers);
        question.delete();
        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 질문_삭제_내_답변만_포함() {
        answers.add(answer1);
        answers.add(answer2);
        question.setAnswers(answers);
        question.delete();
        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 질문_삭제_다른사람_답변_포함() {
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        question.setAnswers(answers);
        question.delete();
        assertThat(question.isDeleted()).isEqualTo(false);
    }
}