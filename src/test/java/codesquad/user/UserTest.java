package codesquad.user;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class UserTest {

    private User user1 = new User();
    private User user2 = new User();
    private String sessionUser = "1234";

    @Before
    public void setUp() {
        user1.setPassword("1234");
        user2.setPassword("4321");
    }

    @Test
    public void 비밀번호_맞을때() {
        assertThat(user1.matchPassword(sessionUser)).isTrue();
    }

    @Test
    public void 비밀번호_틀릴때() {
        assertThat(user2.matchPassword(sessionUser)).isFalse();
    }
}