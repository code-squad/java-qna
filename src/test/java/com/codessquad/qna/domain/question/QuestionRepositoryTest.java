package com.codessquad.qna.domain.question;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 페이징이_잘되는지_테스트() {
        final int addPostCount = 100;
        final int pageSize = 15;
        final int defaultSize = questionRepository.countByIsDeletedFalse();

        int questionSize = addPostCount + defaultSize;
        int pageCount = questionSize / pageSize;
        int lastPagePostCount = questionSize - (pageSize * pageCount);

        userRepository.save(new User("test", "test", "테스터", "test@codesquad.co.kr"));

        for (int i = 0; i < addPostCount; i++) {
            questionRepository.save(new Question(userRepository.findByUserId("test").orElseGet(User::new), "test" + i, "contents" + i));
        }

        for (int i = 0; i < pageCount + 1; i++) {
            PageRequest pageRequest = PageRequest.of(i, pageSize, Sort.by("createdDateTime").descending());
            List<Question> questionPage = questionRepository.findAllByIsDeletedFalse(pageRequest).getContent();
            if (i != pageCount) {
                assertThat(questionPage.size()).isEqualTo(pageSize);
            } else {
                assertThat(questionPage.size()).isEqualTo(lastPagePostCount);
                assertThat(questionPage.get(lastPagePostCount - 1).getWriter().getUserName()).isEqualTo("디온");
            }
        }
    }

}
