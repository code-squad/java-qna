package codesquad.answer;

import codesquad.question.Question;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AnswerTest {
    private User loginUser, other;
    private Question question;
    private Answer answer1, answer2, answer3;
    private List<Answer> answers;

    @Before
    public void setBases() {
        loginUser = new User(10, "zingo", "test", "징고", "zingo@net");
        other = new User(11, "vamboo", "test", "뱀부", "vamboo@net");
        question = new Question(loginUser, "test title", "test contents");
        answer1 = new Answer(question, loginUser, "test answer by logged in user");
        answer2 = new Answer(question, other, "test answer by other user");
    }

//    @Test
//    public void update() {
//        Answer target = new Answer(question, loginUser, "updated contents");
//        answer1.update(target);
//        assertEquals(answer1, target);
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void update_failed_others_comment() {
//        Answer target = new Answer(question, other, "updated contents");
//        answer1.update(target);
//    }

    @Test
    public void delete() {
        answer1.delete(loginUser);
        assertEquals(answer1.isDeleted(), true);
    }

    @Test(expected = IllegalStateException.class)
    public void delete_failed_others_comment() {
        answer1.delete(other);
    }
}