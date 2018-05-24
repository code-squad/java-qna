package codesquad.domain;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepo;

    @After
    public void clean() {
        questionRepo.deleteAll();
    }

    @Test
    public void enroll_timestamp() {
        LocalDateTime now = LocalDateTime.now();
        questionRepo.save(
                Question.builder()
                        .title("첫 글")
                        .contents("안녕하세요")
                        .build()
        );
        Question question = questionRepo.findById(1L).get();
        assertTrue(now.isBefore(question.getCreatedDate()));
        assertTrue(now.isBefore(question.getModifiedDate()));
    }
}