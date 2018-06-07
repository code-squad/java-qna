package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionPageableTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionPageableTest.class);
    QuestionPageable questionPageable;
    Page<Question> questionPage;

    @Autowired
    QuestionRepository questionRepository;

    @Before
    public void setUp() {
        questionPageable = new QuestionPageable();
    }

    @Test
    public void getPageSize() {
        assertThat(questionPageable.getPageSize(), is(15));
    }

    @Test
    public void getSort() {
        Sort sortByIdOrderingDesc = new Sort(Sort.Direction.DESC, "id");
        assertThat(questionPageable.getSort(), is(sortByIdOrderingDesc));
    }

    @Test
    public void getQuestionPage() {
        questionPage = questionRepository.findAll(questionPageable);
        log.debug("Question page total element is {}", questionPage.getTotalElements());
        assertThat(questionPage.getTotalPages(),is(2));
    }

    @Test
    public void getPagesCount() {

    }
}
