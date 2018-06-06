package codesquad.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuestionPageableTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionPageableTest.class);
    QuestionPageable questionPageable;

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
}
