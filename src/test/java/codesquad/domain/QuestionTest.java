package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuestionTest {
    User user1;
    User user2;
    Question question1;
    Question question2;

    @Before
    public void setUp() {
        user1 = new User();
        user1.setId((long) 1);
        user2 = new User();
        user2.setId((long) 2);

        question1 = new Question(user1, "질문 1번 제목", "질문 1번 내용");
        question2 = new Question(user2, "질문 2번 제목", "질문 2번 내용");
    }

    @Test
    public void update() {
        Question editQuestion = new Question(user1, "질문 1번 수정된 제목", "질문 1번 수정된 내용");
        assertThat(question1.getTitle(), is("질문 1번 제목"));
        assertThat(question1.getContents(), is("질문 1번 내용"));
        question1.update(editQuestion, user1);
        assertThat(question1.getTitle(), is("질문 1번 수정된 제목"));
        assertThat(question1.getContents(), is("질문 1번 수정된 내용"));
    }

    @Test
    public void isMatchUserId() {
        User editingUser = new User();
        editingUser.setId((long)1);
        assertThat(question1.isMatchedUserId(editingUser), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void decreaseCount_fail() {
        // answersCount is 0;
        question1.decreaseAnswersCount();
    }

    @Test
    public void increaseCount() {
        question1.increaseAnswersCount();
        assertThat(question1.getAnswersCount(), is(1));
    }
}
