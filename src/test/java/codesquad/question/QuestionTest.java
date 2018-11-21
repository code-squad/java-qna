package codesquad.question;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    Question question1;
    Question question2;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User(new Long(27), "brad903", "1234", "브래드", "brad903@naver.com");
        question1 = new Question();
        question1.setUser(user);
    }

    @Test
    public void matchId() {
        assertThat(question1.matchUser(user)).isEqualTo(true);
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
}