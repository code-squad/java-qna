package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private User user, newUser;

    @Before
    public void setUp() {
        user = new User();
        user.setId((long)1);
        user.setName("황태원");
        user.setPassword("1234");
        user.setEmail("learner@gmail.com");

        newUser = new User();
        newUser.setId((long)2);
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

    @Test
    public void equals() {
        log.debug("user id : {}, newUser id : {}", user.getId(), newUser.getId());
        assertThat(user.equals(newUser), is(false));
    }
}
