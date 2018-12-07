package codesquad.domain.question;

import codesquad.domain.answer.Answer;
import codesquad.exception.UserException;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class QuestionTest {

    private User user1;
    private User user2;
    private Question question1;
    private Question question2;
    private Answer answer1;
    private Answer answer2;

    @Before
    public void before() {
        user1 = new User( 1,"jar100", "test", "백경훈", "jrs111@naver.com");
        user2 = new User( 2,"peter100", "test", "백경훈", "jrs111@naver.com");
        question1 = new Question(user1, "으하하하", "후훗");
        question2 = new Question(user1, "gjgjgj", "dddd훗");
        answer1 = new Answer(question1, user1, "나는 댓글입니다.");
        answer2 = new Answer(question1, user2, "나는 댓글입니다.222");

    }

    @Test
    public void addAnswerTest() {

        Question question = new Question(user1, "으하하하", "후훗");
        question.addAnswer(answer1);
        System.out.println(question.getAnswers());
        System.out.println(question.getCount());
        assertThat(question.getCount()).isEqualTo(1);

    }

    @Test
    public void update() {
        question1.update(question2);
        assertThat(question1.getAnswers()).isEqualTo(question2.getAnswers());
    }

    @Test(expected = UserException.class)
    public void updateFalse() {
        Question newQuestion = new Question(user2, "ghghg21h", "후훗");
        System.out.println(user1.getId());
        question1.update(newQuestion);
        System.out.println(question1.getTitle());
        System.out.println(newQuestion.getTitle());
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
        assertThat(question1.getCount()).isEqualTo(n);
    }


    @Test
    public void count() {
        question1.addAnswer(answer1);
        question1.addAnswer(answer2);
        question1.addAnswer(answer2);

        assertThat(question1.countOfMatchUser()).isEqualTo(1);
    }
}