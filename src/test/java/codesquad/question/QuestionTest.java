package codesquad.question;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class QuestionTest {

    private Question question1 = new Question();

    private Question question2 = new Question();

    private User sessionUser = new User();

    private User diffrentUser = new User();

    private User loginUser = new User();
    @Before
    public void setUp() {
        loginUser.setUserId("kuro");
        sessionUser.setUserId("kuro");
        diffrentUser.setUserId("byeol");
        question1.setWriter(loginUser);
        question2.setWriter(loginUser);
    }

    @Test
    public void 아이디가_맞는지() {
        assertThat(question1.matchUserId(sessionUser.getUserId())).isTrue();
    }

    @Test
    public void 아이디가_틀릴때() {
        assertThat(question2.matchUserId(diffrentUser.getUserId())).isFalse();
    }

}