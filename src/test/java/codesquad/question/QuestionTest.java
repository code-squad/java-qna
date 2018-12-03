package codesquad.question;

import codesquad.answer.Answer;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionTest {
    private User loginUser, other;
    private Question question;
    private Answer answer1, answer2, answer3;
    private List<Answer> answers;

    @Before
    public void setBases() {
        loginUser = new User(10, "zingo", "test", "징고", "zingo@net");
        other = new User(11, "vamboo", "test", "뱀부", "vamboo@net");
        question = new Question(loginUser, "test title", "test contents");
        answer1 = new Answer(question, loginUser, "test answer1");
        answer2 = new Answer(question, loginUser, "test answer2");
        answer3 = new Answer(question, other, "test answer by other user");
    }

//    @Test
//    public void update() {
//        Question target = new Question(loginUser, "updated title", "updated contents");
//        question.update(target);
//        assertEquals(question, target);
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void update_failed() {
//        Question target = new Question(other, "updated title", "updated contents");
//        question.update(target);
//        assertEquals(question, target);
//    }

    @Test
    public void delete() {
        question.delete(loginUser);
        assertEquals(question.isDeleted(), true);
    }

    @Test(expected = IllegalStateException.class)
    public void delete_failed_otherUser() {
        question.delete(other);
    }

    @Test
    public void delete_failed_otherUsersAnswerExist() {
        answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3));
        question.setAnswers(answers);

        question.delete(loginUser);
        assertEquals(question.isDeleted(), false);
        //TODO 삭제불가 시 그냥 false로 두지말고 예외발생하도록 개선하기
    }
}