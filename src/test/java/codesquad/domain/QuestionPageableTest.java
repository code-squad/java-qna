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

public class QuestionPageableTest {
    private static final Logger log =  LoggerFactory.getLogger(QuestionPageableTest.class);

    @Before
    public void setUp() {
    }

    @Test
    public void getPageSize() {
        QuestionPageable questionPageable = new QuestionPageable();
        assertThat(questionPageable.getPageSize(), is(15));
    }
}
