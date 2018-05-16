package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = User.builder().name("colin").userId("imjinbro").passwd("1234").build();
    }

    @Test
    public void valid_change_passwd() {
        user.changeInfo("1234", User.builder().name("colin").userId("imjinbro").passwd("1234").build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_change_passwd() {
        user.changeInfo("123567", User.builder().name("colin").userId("imjinbro").passwd("1234").build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalid_change_passwd_2() {
        user.changeInfo(null, new User());
    }
}