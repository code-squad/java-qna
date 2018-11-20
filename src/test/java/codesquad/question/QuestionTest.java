package codesquad.question;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    Question question1;
    Question question2;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User(new Long(27), "brad903", "1234", "브래드", "brad903@naver.com");
        question1 = new Question();
        question1.setUser(user);
    }

    @Test
    public void matchId() {
        assertThat(question1.matchUser(user)).isEqualTo(true);
    }
}