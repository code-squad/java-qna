package codesquad.web;

import codesquad.web.model.User;
import codesquad.web.model.Users;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class UsersTest {

    Users users;

    @Test
    public void findUser() {
        users = new Users();

        users.addUser(new User("asdf", "ddd", "sdfsa", "223@asdf.com"));
        User user = users.findUser("asdf");
        assertThat(user.getUserId(), is("asdf"));
    }
}
