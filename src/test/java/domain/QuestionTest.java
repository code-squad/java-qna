package domain;

import codesquad.domain.Question;
import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuestionTest {
    private User loginUser;
    private Question question;
    private User user;

    @Before
    public void setUp() {
        loginUser = new User();
        loginUser.setName("jimmy");
        loginUser.setEmail("jaeyeon93@naver.com");
        loginUser.setPassword("12345");
        loginUser.setUserId("jaeyeon93");

        user = new User();
        user.setName("jimmy");
        user.setEmail("jaeyeon93@naver.com");
        user.setPassword("12345");
        user.setUserId("jaeyeon93");
        question = new Question(user,"before title", "before content");

    }

    @Test
    public void sameWriterTest() {
        boolean result = question.isSameWriter(loginUser);
        assertTrue(result);
    }

    @Test
    public void updateTest() {
        System.out.println("Before update " + question.toString());
        Question updateQuestion = question.update2("after title", "afterContents", loginUser);
        System.out.println("After update " + updateQuestion.toString());
    }
}
