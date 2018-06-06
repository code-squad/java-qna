package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Null;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private User user, newUser;

    @Before
    public void setUp() {
        user = new User("1", "learner", "1234", "황태원", "learner@gmail.com");
        newUser = new User("2", "learner", "1234", "황태원", "hardLearner@gmail.com");
    }

    @Test
    public void update_hasNotNewPassword() {
        user.update(newUser, "");
        assertThat(user.isMatchedPassword("1234"),is(true));
    }

    @Test
    public void update_hasNewPassword() {
        String newPassword = "4321";
        user.update(user, newPassword);
        assertThat(user.isMatchedPassword(newPassword), is(true));
    }

    @Test
    public void isMatchedPassword_fail() {
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
