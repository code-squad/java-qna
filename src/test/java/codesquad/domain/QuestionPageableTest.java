package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionPageableTest {
    private static final Logger log =  LoggerFactory.getLogger(QuestionPageableTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Question question;
    private Pageable pageable;

    @Before
    public void setUp() {
        User user = new User("taewon", "1234", "hwangTaewon", "learner@gmail.com");
        userRepository.save(user);
        question = new Question(user, "질문 페이징 제목", "질문 페이징 내용");
        questionRepository.save(question);

        pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 15;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    @Test
    public void findAllQuestion() {
        List<Question> questionList = questionRepository.findAll();
        log.debug("Question list size is {}", questionList.size());
        assertThat(questionList.size() > 0, is(true));
    }

    @Test
    public void findAllPageable() {
        // Currently, default page size is 15
        // Total question count is over 15
        Page<Question> questionPage = questionRepository.findAll(pageable);
        assertThat(questionPage.getSize(),is(15));
    }


}
