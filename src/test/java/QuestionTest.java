import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import codesquad.domain.Question;

public class QuestionTest {

	Question question;
	
	
	@Before
	public void setUp() {
		question = new Question("gram","제목","내용");
	}

	@Test
	public void updateTest() {
		question.update("제목2", "내용2");
		assertThat(question, is(question.update("제목2","내용2")));
	}


}
