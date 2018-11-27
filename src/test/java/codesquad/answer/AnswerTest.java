package codesquad.answer;

import codesquad.exception.UserException;
import codesquad.question.Question;
import codesquad.user.User;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AnswerTest {
    User user1 = new User(1, "jar100", "test", "백경훈", "jrs111@naver.com");
    User user2 = new User(2, "peter100", "test", "백경훈", "jrs111@naver.com");

    Question question1 = new Question(user1, "으하하하", "후훗");
    Question question2 = new Question(user1, "gjgjgj", "dddd훗");

    Answer answer1 = new Answer(question1, user1, "나는 댓글입니다.");
    Answer answer2 = new Answer(question1, user2, "나는 댓글입니다.222");

    @Test
    public void matchUserTrue() {
        assertThat(answer1.matchUser(user1)).isTrue();
    }

    @Test()
    public void matchUserFalse() {
        assertThat(answer1.matchUser(user2)).isFalse();
    }

    @Test
    public void updateTrue() {
        Answer newAnswer = new Answer(question1,user1,"이걸로 바껴야함");
        answer1.update(newAnswer);
        assertThat(answer1.getContents()).isEqualTo(newAnswer.getContents());
    }

    @Test(expected = UserException.class)
    public void updateFalse() {
        Answer newAnswer = new Answer(question1,user2,"이걸로 바뀌면안되야함");
        answer1.update(newAnswer);
        assertThat(answer1.getContents()).isEqualTo(newAnswer.getContents());
    }



    @Test
    public void deleted() {
        answer1.deleted();
        assertThat(answer1.isDeleted()).isTrue();
    }
}