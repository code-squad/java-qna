package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    private User user, newUser;

    @Before
    public void setUp() {
        user = new User();
        user.setName("황태원");
        user.setPassword("1234");
        user.setEmail("learner@gmail.com");

        newUser = new User();
        newUser.setName("황태원");
        newUser.setPassword("1234");
        newUser.setEmail("hardLearner@gmail.com");
    }

    @Test
    public void update_hasnotNewPassword() {
        user.update(newUser, "");
        assertThat(user.getEmail(), is(newUser.getEmail()));
    }

    @Test
    public void update_hasNewPassword() {
        User newPasswordUser = new User();
        newPasswordUser.setPassword("4321");
        user.update(newUser, "4321");
        assertThat(user.isMatchedPassword(newPasswordUser), is(true));
    }

    @Test
    public void isSamePassword_pass() {
        // 1234.equals(1234)
        assertThat(user.isMatchedPassword(newUser), is(true));
    }

    @Test
    public void isSamePassword_fail() {
        newUser.setPassword("4321");
        // 1234.equals(4321)
        assertThat(user.isMatchedPassword(newUser), is(false));
    }
}
