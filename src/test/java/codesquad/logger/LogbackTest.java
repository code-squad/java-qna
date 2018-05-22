package codesquad.logger;

import codesquad.domain.question.Question;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
    private static final Logger log = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void print() {
        log.info("INFO : {}", Question.builder().title("test title").contents("test content").build());
    }
}
