package codesquad.question.answer;

import codesquad.question.Question;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    Answer answer;
    User user;
    User user2;

    @Before
    public void setUp() throws Exception {
        Question question = new Question();
        user = new User(25, "leeh903", "1234", "이정현", "leejh903@naver.com");
        user2 = new User(27, "leeh903", "1234", "이정현", "leejh903@naver.com");
        answer = new Answer(question, user, "aaa", false);
    }

    @Test
    public void 같은_유저_확인() {
        assertThat(answer.isSameUser(user)).isEqualTo(true);
    }

    @Test
    public void 같은_유저_확인2() {
        assertThat(answer.isSameUser(user2)).isEqualTo(false);
    }
}