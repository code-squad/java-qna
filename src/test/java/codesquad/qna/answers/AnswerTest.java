package codesquad.qna.answers;

import codesquad.qna.questions.Question;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    Answer answer;
    Answer otherAnswer;
    User user;
    User user2;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1);
        user.setUserId("1");

        user2 = new User();
        user2.setId(2);
        user2.setUserId("2");

        Question q1 = new Question();
        Question q2 = new Question();
        q1.setId(1);
        q2.setId(2);
        q1.setWriter(user);
        q2.setWriter(user2);

        answer = new Answer(user, q1, "12345");
        otherAnswer = new Answer(user2, q2, "123456");
    }

    @Test
    public void updateContents() {
        answer.updateContents("789", user);
        assertThat(answer.getContents()).isEqualTo("789");
    }

    @Test(expected = IllegalStateException.class)
    public void updateContentsWithException() {
        answer.updateContents("789", user2);
    }

    @Test
    public void matchWriter() {
        assertThat(answer.matchWriter(user)).isTrue();
        assertThat(answer.matchWriter(user2)).isFalse();
    }

    @Test(expected = IllegalStateException.class)
    public void deleteWithException() {
        answer.delete(user2);
    }

    @Test
    public void delete() {
        answer.delete(user);
    }


}