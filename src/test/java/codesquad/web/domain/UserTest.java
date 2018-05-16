package codesquad.web.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = User.builder().name("name")
                .password("password")
                .email("email@email")
                .userId("hello").build();
    }

    @Test
    public void passwordMatch_true() {
        assertTrue(user.isMatch("password"));
    }

    @Test
    public void passwordMatch_false() {
        assertFalse(user.isMatch("wrong"));
    }
}
