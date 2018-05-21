package domain;

import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    private User loginUser;
    private User user;
    @Before
    public void setUp() {
        loginUser = new User();
        loginUser.setName("jimmy");
        loginUser.setEmail("jaeyeon93@naver.com");
        loginUser.setPassword("12345");
        loginUser.setUserId("jaeyeon93");

        user = new User();
        user.setUserId("jaeyeon93");
    }

    @Test
    public void sameidTest() {
        User user = new User();
        user.setUserId("jaeyeon93");
        boolean result = user.isSameUser(loginUser);
        assertTrue(result);
    }

    @Test
    public void userUpdate() {
        User updatedUser = user.update(loginUser, "11111", "jimmyEdit", "edit@naver.com");
        System.out.println(updatedUser.toString());
    }
}
