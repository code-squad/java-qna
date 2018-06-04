package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AnswerTest {
    User user1;
    User user2;
    Question question1;
    Answer answer1;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setId((long) 1);
        user2 = new User();
        user2.setId((long) 2);

        question1 = new Question(user1, "질문 1번 제목", "질문 1번 내용");
        answer1 = new Answer(user1, question1, "질문 1번 첫 번째 답변");
    }

    @Test
    public void update() {
        Answer editedAnswer = new Answer(user1, question1, "질문 1번 수정된 첫 번째 답변");
        answer1.update(editedAnswer, user1);
        assertThat(answer1.getComment(), is("질문 1번 수정된 첫 번째 답변"));
    }

    @Test
    public void update_fail() {
        Answer editedAnswer = new Answer(user1, question1, "질문 1번 수정된 첫 번째 답변");
        assertThat(answer1.update(editedAnswer, user2), is(false));
    }
}