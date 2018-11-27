package codesquad.qna;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionRepositoryTest {
    @Autowired
    QuestionRepository questionRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryTest.class);

    @Test
    public void findByUserId() {
        Long userId = new Long(2);

        System.out.println("Null");
        for (Object question : questionRepository.findByUserId(userId)) {
            logger.debug("is here {}", question);
            assertTrue(question instanceof Question);
        }
    }
}