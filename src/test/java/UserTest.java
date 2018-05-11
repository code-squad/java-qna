import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import codesquad.domain.User;

public class UserTest {

	User user;

	@Before
	public void setUp() {
		user = new User();
		user.setPassword("1234");
	}

	@Test
	public void checkTest() {
		assertThat(user.matchPassword("1234"), is(true));
	}

}
