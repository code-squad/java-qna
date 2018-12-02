package codesquad.qna;

import codesquad.answer.Answer;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    private User user;
    private User user1;
    private Question q;
    private Question q2;
    private Question updateQ;

    @Before
    public void init() {
        user = new User(1L, "user1", "user1", "user1", "1@1");
        user1 = new User(2L, "user2", "user2", "user2", "2@2");
        q = new Question(user, "title", "contents");
        q.setId(1L);
        q2 = new Question(user, "title", "contents");
        q2.setId(2L);
        updateQ = new Question(user, "updateTitle", "updateContents");
        List<Answer> answersSameWriter = Arrays.asList(new Answer(user, q, "11"), new Answer(user, q, "22"));
        List<Answer> answersDefferentWriter = Arrays.asList(new Answer(user1, q, "11"), new Answer(user1, q, "22"), new Answer(user, q, "33"));
        q.setAnswers(answersSameWriter);
        q2.setAnswers(answersDefferentWriter);
    }

    @Test
    public void updateTrue() {
        assertThat(q.update(updateQ, user)).isTrue();
    }

    @Test
    public void updateFalse() {
        assertThat(q.update(updateQ, user1)).isFalse();
    }

    @Test
    public void delete_답변이없는경우() {
        assertThat(q.delete()).isTrue();
    }

    @Test
    public void delete_답변작성자같은경우() {
        assertThat(q.delete()).isTrue();
    }

    @Test
    public void delete_답변작성자다른경우() {
        assertThat(q2.delete()).isFalse();
    }
}
