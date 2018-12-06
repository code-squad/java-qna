package codesquad.answer;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    private User sessionUser = new User();

    private User diffrentUser = new User();

    private Answer answer1 = new Answer();

    @Before
    public void setUp() {
        diffrentUser.setId(4);
        sessionUser.setId(3);
        answer1.setWriter(sessionUser);
    }

    @Test
    public void 사용자가_맞는지() {
        assertThat(answer1.isSameWriter(sessionUser)).isTrue();
    }

    @Test
    public void 사용자가_틀릴때() {
        assertThat(answer1.isSameWriter(diffrentUser)).isFalse();
    }
}