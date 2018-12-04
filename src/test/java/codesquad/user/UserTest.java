package codesquad.user;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    User user1;
    User user2;
    User otherUser;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user2 = new User();
        otherUser = new User();

        user1.setId(1);
        user1.setUserId("choising");
        user1.setPassword("1234");
        user2.setId(1);
        user2.setUserId("choising");
        user2.setPassword("4567");

        otherUser.setId(3);
        otherUser.setUserId("seungmin");
    }

    @Test
    public void isUser() {
        assertThat(user1.isUser("choising")).isTrue();
        assertThat(otherUser.isUser("seungmin")).isTrue();
    }

    @Test
    public void matchPassword() {
        assertThat(user1.matchPassword(user2)).isFalse();
        assertThat(user1.matchPassword("1234")).isTrue();
    }

    @Test
    public void matchId() {
        assertThat(user1.matchId(1)).isTrue();
    }
}