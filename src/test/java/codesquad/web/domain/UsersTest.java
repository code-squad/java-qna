package codesquad.web.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


public class UsersTest {

    Users users;

    @Before
    public void setUp() {
        users = new Users();
    }

    @Test
    public void findUser() {
        users.addUser(new User("asdf", "ddd", "sdfsa", "223@asdf.com"));
        User user = users.findUser("asdf");
        assertThat(user.getUserId(), is("asdf"));
    }

    @Test
    public void matchUser_true() {
        users.addUser(new User("a", "p", "n", "c@d.com"));
        User user = users.matchUser("a", "p");
        assertThat(user, is(new User("a", "p", "n", "c@d.com")));
    }

    @Test
    public void matchUser_null() {
        users.addUser(new User("a", "p", "n", "c@d.com"));
        User user = users.matchUser("a", "a");
        assertTrue(user == null);
    }
}
