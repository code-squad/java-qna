package codesquad.question;

import codesquad.question.answer.Answer;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    Question question1;
    User user1, user2;
    Answer answer1, answer2, answer3;
    List<Answer> answers;

    @Before
    public void setUp() throws Exception {
        user1 = new User(27, "brad903", "1234", "브래드", "brad903@naver.com");
        user2 = new User(28, "brad903", "1234", "브래드", "brad903@naver.com");
        question1 = new Question(1, "aaa", "bbb", user1);
        answer1 = new Answer(question1, user1, "aaa", false);
        answer2 = new Answer(question1, user1, "aaa", false);
        answer3 = new Answer(question1, user2, "aaa", false);
    }

    @Test
    public void matchId() {
        assertThat(question1.isSameUser(user1)).isEqualTo(true);
    }

    @Test
    public void LocalDateTime() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime.truncatedTo(ChronoUnit.MINUTES);

        System.out.println("date : " + date);
        System.out.println("time : " + time);
        System.out.println("localDateTime : " + localDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    public void 답변_다른유저_존재할때() {
        answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3));
        question1.setAnswers(answers);
        assertThat(question1.canDeleteAnswer()).isEqualTo(false);
    }

    @Test
    public void 답변_다른유저_존재하지않을때() {
        answers = new ArrayList<>(Arrays.asList(answer1, answer2));
        question1.setAnswers(answers);
        assertThat(question1.canDeleteAnswer()).isEqualTo(true);
    }

    @Test
    public void 답변_없을때() {
        answers = new ArrayList<>();
        question1.setAnswers(answers);
        assertThat(question1.canDeleteAnswer()).isEqualTo(true);
    }

    @Test(expected = IllegalStateException.class)
    public void deleted() {
        answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3));
        question1.setAnswers(answers);
        question1.deleted();
    }

    @Test
    public void 답변_삭제안된것만_가져오기_다삭제안될었을때() {
        Answer answer4 = new Answer(question1, user2, "aaa", false);
        Answer answer5 = new Answer(question1, user2, "aaa", false);
        answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3, answer4, answer5));
        question1.setAnswers(answers);
        assertThat(question1.getNotDeletedAnswers()).isEqualTo(answers);
    }

    @Test
    public void 답변_삭제안된것만_가져오기_삭제된것있을때() {
        Answer answer4 = new Answer(question1, user2, "aaa", true);
        Answer answer5 = new Answer(question1, user2, "aaa", true);
        answers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3, answer4, answer5));
        question1.setAnswers(answers);
        List<Answer> newAnswers = new ArrayList<>(Arrays.asList(answer1, answer2, answer3));
        assertThat(question1.getNotDeletedAnswers()).isEqualTo(newAnswers);
    }
}