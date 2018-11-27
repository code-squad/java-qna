package codesquad.qna;

import codesquad.user.User;
import codesquad.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    @Test
    public void updateTrue() {
        User user = new User();
        user.setId((long)1);
        Question q = new Question(user, "title", "contents");
        Question updateQ = new Question(user, "updateTitle", "updateContents");
        assertThat(q.update(updateQ, user)).isTrue();
    }

    @Test
    public void updateFalse() {
        User user = new User();
        user.setId((long)1);
        User user1 = new User();
        user1.setId((long)2);
        Question q = new Question(user, "title", "contents");
        Question updateQ = new Question(user1, "updateTitle", "updateContents");
        assertThat(q.update(updateQ, user1));
    }
}
