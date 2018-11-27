package codesquad.user;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    User user;

    @Before
    public void before() {
        user = new User(1, "userId", "password", "name", "email@e.e");
    }

    @Test
    public void checkPassword() {
        User samePwdUser = new User(2, null, "password", null, null);
        assertThat(user.checkPassword(samePwdUser)).isTrue();

        User diffPwdUser = new User(2, null, "diffPassword", null, null);
        assertThat(user.checkPassword(diffPwdUser)).isFalse();
    }

    @Test
    public void checkPassword1() {
        assertThat(user.checkPassword("password")).isTrue();
        assertThat(user.checkPassword("diffPassword")).isFalse();
    }

    @Test
    public void updateNameEmail() {
        User updateInfo = new User(-1, null, null, "newName", "newEmail@n.com");
        user.updateNameEmail(updateInfo);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo("newEmail@n.com");
        assertThat(user.getName()).isEqualTo("newName");
        assertThat(user.getPassword()).isEqualTo("password");
    }
}