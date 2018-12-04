package codesquad.qna.questions;

import codesquad.qna.answers.Answer;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    Question q1;
    Question q2;
    User user;

    @Before
    public void setUp() throws Exception {
        q1 = new Question();
        q2 = new Question();
        q1.setId(1);
        q2.setId(2);
        user = new User();
        user.setId(1);
        user.setUserId("choising");
        user.setPassword("1234");
        q1.setWriter(user);
        q2.setWriter(user);
    }

    @Test
    public void matchWriter() {
        assertThat(q1.matchWriter(user)).isTrue();
        User user2 = new User();
        user2.setId(2);
        user2.setUserId("seung");
        assertThat(q2.matchWriter(user2)).isFalse();
    }

    @Test(expected = IllegalStateException.class)
    public void delete() {
        User user2 = new User();
        user2.setId(2);
        user2.setUserId("seung");
        q1.delete(user2);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteAnswers() {
        User user2 = new User();
        user2.setId(2);
        user2.setUserId("seung");
        Answer answer1 = new Answer(user, q1, "2134");
        Answer answer2 = new Answer(user2, q1, "1234");
        List<Answer> list = new ArrayList<>();
        list.add(answer1);
        list.add(answer2);

        q1.setAnswers(list);
        q1.delete(user);
    }
}