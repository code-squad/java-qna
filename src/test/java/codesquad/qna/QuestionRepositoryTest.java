package codesquad.qna;

import codesquad.answer.Answer;
import codesquad.user.User;
import codesquad.user.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryTest.class);

    @Test
    public void findByUserId() {
        Long userId = new Long(2);

        for (Object question : questionRepository.findByUserId(userId)) {
            logger.debug("is here {}", question);
            assertTrue(question instanceof Question);
        }
    }

    @Test
    public void getAliveAnswers() {
        Long questionId = new Long(1);

        for (Answer answer : questionRepository.findById(questionId).orElse(null).getAliveAnswers()) {
            logger.debug("r u deleted? : {}", answer.isDeleted());
        }
    }

    @Test
    public void deleteState() {
        Long questionId = new Long(1);
        Long userId = new Long(2);
        User user = userRepository.findById(userId).orElse(null);
        Question question = questionRepository.findById(questionId).orElse(null);

        assertTrue(question.deleteState(user));

    }
}