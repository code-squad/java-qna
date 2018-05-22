package codesquad.domain;

import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = User.builder().name("colin").userId("imjinbro").passwd("1234").build();
        user.setId(1L);
    }

    @Test
    public void valid_passwd_change_passwd() {
        User updateInfo = User.builder().name("colin").userId("imjinbro").passwd("1234").build();
        updateInfo.setId(1L);
        user.update(updateInfo,  "1234", updateInfo);
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_passwd_change_passwd() {
        User updateInfo = User.builder().name("colin").userId("imjinbro").passwd("456789").build();
        updateInfo.setId(1L);
        user.update(updateInfo, "1234567", updateInfo);
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_passwd_change_passwd_2() {
        User updateInfo = new User();
        updateInfo.setId(1L);
        user.update(updateInfo, null, new User());
    }

    @Test
    public void valid_id_change_passwd() {
        User updateInfo = User.builder().name("colin").userId("imjinbro").passwd("456789").build();
        updateInfo.setId(1L);
        user.update(updateInfo,  "1234", updateInfo);
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_id_change_passwd() {
        User updateInfo = User.builder().name("colin").userId("imjinbro").passwd("456789").build();
        updateInfo.setId(2L);
        user.update(updateInfo, "1234", updateInfo);
    }

    @Test
    public void match_password() {
        assertTrue(user.isMatch("1234"));
    }

    @Test
    public void invalid_match_password() {
        assertFalse(user.isMatch("12345678"));
    }

    @Test
    public void equals() {
        User sessionUser = new User();
        sessionUser.setId(1L);
        assertEquals(user, sessionUser);
    }

    @Test
    public void not_equals() {
        User sessionUser = new User();
        sessionUser.setId(3L);
        assertNotEquals(user, sessionUser);
    }
}