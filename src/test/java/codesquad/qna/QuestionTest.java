package codesquad.qna;

import codesquad.user.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class QuestionTest {
    private static final Logger logger = LoggerFactory.getLogger(QuestionTest.class);

    @Test
    public void localDateTimeTest() {
        User user = new User();
        Question q = new Question(user, "title", "contents");

        logger.debug("now? {}", q.getCreateDate());
        logger.debug("now formatted? {}", q.getFormattedCreateDate());
    }

    @Test
    public void getNewLineContents() {
        User user = new User();
        Question q = new Question(user, "title", "contents\ncontents2\ncontent3");

        logger.debug("newLineContents : {}", q.getNewLineContents());
    }

}