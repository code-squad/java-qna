import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import codesquad.domain.Question;
import codesquad.domain.User;

public class QuestionTest {

	Question question;
	User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setName("이그램");
		user.setPassword("1234");
		user.setEmail("gram@codesqaud");
		question = new Question(user,"제목","내용");
	}

	@Test
	public void updateTest() {
		question.update("제목2", "내용2");
		assertThat(question, is(question.update("제목2","내용2")));
	}


}
