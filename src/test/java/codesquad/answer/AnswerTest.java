package codesquad.answer;

import codesquad.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class AnswerTest {
    Answer answer;
    User loginUser;
    User otherUser;

    @Before
    public void setUp() throws Exception {
        loginUser = new User();
        otherUser = new User();
        answer = new Answer();
        loginUser.setPId(1);
        otherUser.setPId(2);
        answer.setWriter(loginUser);
    }

    @Test
    public void 사용자_일치_판단(){
        assertThat(answer.matchUser(loginUser)).isEqualTo(true);
        assertThat(answer.matchUser(otherUser)).isEqualTo(false);
    }

    @Test
    public void 답변_삭제() {
        answer.delete(answer.getWriter());
        assertThat(answer.isDeleted()).isEqualTo(true);
    }
}