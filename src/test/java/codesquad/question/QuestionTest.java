package codesquad.question;

import codesquad.answer.Answer;
import codesquad.exception.UserException;
import codesquad.user.User;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class QuestionTest {
    User user1 = new User(1, "jar100", "test", "백경훈", "jrs111@naver.com");
    User user2 = new User(2, "peter100", "test", "백경훈", "jrs111@naver.com");

    Question question1 = new Question(user1, "으하하하", "후훗");
    Question question2 = new Question(user1, "gjgjgj", "dddd훗");

    Answer answer1 = new Answer(question1, user1, "나는 댓글입니다.");
    Answer answer2 = new Answer(question1, user2, "나는 댓글입니다.222");

    @Test
    public void addAnswerTest() {
        Question question = new Question(user1, "으하하하", "후훗");
        question.addAnswer(answer1);
        System.out.println(question.getAnswers());
        System.out.println(question.getConut());
        assertThat(question.getConut()).isEqualTo("1");

    }

    @Test
    public void update() {
        question1.update(question2);
        assertThat(question1.getAnswers()).isEqualTo(question2.getAnswers());
    }

    @Test(expected = UserException.class)
    public void updateFalse() {
        Question newQuestion = new Question(user2, "ghghgh", "후훗");
        question1.update(newQuestion);
        assertThat(question1.getTitle()).isEqualTo(newQuestion.getTitle());
    }

    @Test(expected = UserException.class)
    public void 삭제실패() {
        question1.addAnswer(answer1);
        question1.addAnswer(answer2);
        question1.deleted();
        System.out.println(question1.getDeleted());
        assertThat(question1.getDeleted()).isTrue();
    }

    @Test
    public void 삭제성공() {
        question1.addAnswer(answer1);
        question1.addAnswer(answer1);
        question1.deleted();
        System.out.println(question1.getDeleted());
        assertThat(question1.getDeleted()).isTrue();
    }

    @Test
    public void 댓글수() {
        int n = 4;
        for (int i = 0; i < n; i++) {
            question1.addAnswer(answer1);

        }
        assertThat(question1.getConut()).isEqualTo(String.valueOf(n));
    }


    @Test
    public void count() {
        question1.addAnswer(answer1);
        question1.addAnswer(answer2);
        question1.addAnswer(answer2);

        assertThat(question1.conutOfMatchUser()).isEqualTo(1);
    }
}