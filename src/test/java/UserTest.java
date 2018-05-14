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
		user.setId((long)1);
		user.setName("이그램");
		user.setPassword("1234");
		user.setEmail("gram@codesqaud");
	}

	@Test
	public void matchIdTest() {
		assertThat(user.matchId((long)1), is(true));
	}
	
	@Test
	public void matchPasswordTest() {
		assertThat(user.matchPassword("1234"), is(true));
	}

	@Test
	public void updateTest() {
		User newUser = new User();
		newUser.setName("이두린");
		newUser.setPassword("1234");
		newUser.setEmail("durin@codesqaud");
		assertThat(user, is(user.update(newUser)));
	}


}
