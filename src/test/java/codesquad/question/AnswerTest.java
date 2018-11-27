package codesquad.question;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    Answer answer;
    User user;

    @Before
    public void before() {
        user = new User(1, "id", "pwd", "name", "email@email.com");
        Question question = new Question(1, user, "title", "contents", new ArrayList<>(), false);
        answer = new Answer(1, user, "contents", question, false);
        question.getAnswers().add(answer);
    }

    @Test
    public void isSameWriter() {
        assertThat(answer.isSameWriter(user)).isTrue();

        User otherUser = new User(2, "id", "pwd", "name", "e@email.com");
        assertThat(answer.isSameWriter(otherUser)).isFalse();
    }

    @Test
    public void delete() {
        answer.delete();
        assertThat(answer.isDeleted()).isTrue();
    }
}