package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuestionTest {
    User user1;
    User user2;
    Question question1;
    Question question2;
    List<Answer> answersHasOtherUser;
    List<Answer> answersHasNotOtherUser;

    @Before
    public void setUp() {
        user1 = new User("1", "learner", "9229", "황태원", "learner@gmail.com");
        user2 = new User("2", "pobi", "9229", "박재성", "pobi@gmail.com");

        question1 = new Question(user1, "질문 1번 제목", "질문 1번 내용");
        question2 = new Question(user2, "질문 2번 제목", "질문 2번 내용");

        answersHasOtherUser = Arrays.asList(
                new Answer(user1, question1, "질문 1번 답변1"),
                new Answer(user1, question1, "질문 1번 답변2"),
                new Answer(user2, question1, "질문 1번 답변3"));

        answersHasNotOtherUser = Arrays.asList(
                new Answer(user1, question1, "질문 1번 답변1"),
                new Answer(user1, question1, "질문 1번 답변2"),
                new Answer(user1, question1, "질문 1번 답변3"));
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
        User editingUser = new User("1", "learner", "9229", "황태원", "learner@gmail.com");
        assertThat(question1.isMatchedUser(editingUser), is(true));
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

    @Test
    public void hasOtherUserAnswers() {
        question1.setAnswers(answersHasOtherUser);
        assertThat(question1.hasOtherUserAnswers(), is(true));
    }

    @Test
    public void hasNotOtherUserAnswers() {
        question1.setAnswers(answersHasNotOtherUser);
        assertThat(question1.hasOtherUserAnswers(), is(false));
    }

    @Test
    public void delete() {
        question1.setAnswers(answersHasNotOtherUser);
        question1.delete();
        assertThat(question1.isDeleted(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void delete_fail() {
        question1.setAnswers(answersHasOtherUser);
        question1.delete();
    }
}
