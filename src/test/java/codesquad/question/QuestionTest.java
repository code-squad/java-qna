package codesquad.question;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;



import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class QuestionTest {

    private User user;
    private User otherUser;
    private Question question;

    @Before
    public void before() {
        user = new User(1,"userId", "password", "name", "a@email.com");
        otherUser = new User(2,"otherId", "password", "otherName", "A@email.com");
        question = new Question(1, user, "title", "contents", new ArrayList<>(), false);
    }

    @Test
    public void isSameWriter() {
        question.isSameWriter(user);
        assertThat(question.isSameWriter(user)).isTrue();
        assertThat(question.isSameWriter(otherUser)).isFalse();
    }

    @Test
    public void 삭제_성공() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, user, "contents1", question, false));
        answers.add(new Answer(2, user, "contents2", question, false));
        question.setAnswers(answers);

        question.deleteBy(user);
        assertThat(question.isDeleted()).isTrue();
        assertThat(answers.get(0).isDeleted()).isTrue();
        assertThat(answers.get(1).isDeleted()).isTrue();
    }

    @Test
    public void 다른_답변자로_삭제_실패() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, otherUser, "contents1", question, false));
        answers.add(new Answer(2, user, "contents2", question, false));
        question.setAnswers(answers);

        question.deleteBy(user);
        assertThat(question.isDeleted()).isFalse();
        assertThat(answers.get(0).isDeleted()).isFalse();
        assertThat(answers.get(1).isDeleted()).isFalse();
    }

    @Test
    public void 답변_없을때(){
        question.deleteBy(user);
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    public void 답변_없지만_사용자_다름(){
        question.deleteBy(otherUser);
        assertThat(question.isDeleted()).isFalse();
    }
}