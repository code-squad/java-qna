package codesquad.answer;

import codesquad.qna.Question;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    private User user1 = new User();
    private User user2 = new User();
    private Question question1 = new Question(user1, "title1", "contents1");
    private Answer answer1 = new Answer(user1, question1, "댓글1");
    private Answer answer2 = new Answer(user1, question1, "댓글2");
    private List<Answer> answers = new ArrayList<>(Arrays.asList(answer1, answer2));

    @Before
    public void init() {
        user1.setId(1L);
        user2.setId(2L);
        question1.setAnswers(answers);
    }

    @Test
    public void isSameWriterTrue() {
        assertThat(answer1.isSameWriter(user1)).isTrue();
    }

    @Test
    public void isSameWriterFalse() {
        assertThat(answer1.isSameWriter(user2)).isFalse();
    }

    @Test
    public void AllWriterSameQeustionWriter() {

    }
}
