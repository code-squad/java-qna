package codesquad.answer;

import codesquad.qna.Question;
import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class AnswerTest {
    private static final Logger logger = LoggerFactory.getLogger(AnswerTest.class);

    User u;
    Question q;
    Answer a;

    @Before
    public void setUp() {
        u = new User();
        q = new Question(u, "title", "contents\ncontents2\ncontent3");
        a = new Answer(q, u, "test");
    }

    @Test
    public void getFormattedDate() {
        logger.debug("getFormattedCreateDate : {}", a.getFormattedCreateDate());
    }

    @Test
    public void deletedState() {
//        assertFalse(a.isDeleted());
        a.deletedState(u);
        assertTrue(a.isDeleted());
    }
}