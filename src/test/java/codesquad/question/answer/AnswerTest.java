package codesquad.question.answer;

import codesquad.question.Question;
import codesquad.user.User;
import codesquad.utils.Result;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class AnswerTest {
    private static final Logger log = getLogger(AnswerTest.class);

    Answer answer;
    User user;
    User user2;

    @Before
    public void setUp() throws Exception {
        Question question = new Question();
        user = new User(25, "leeh903", "1234", "이정현", "leejh903@naver.com");
        user2 = new User(27, "leeh903", "1234", "이정현", "leejh903@naver.com");
        answer = new Answer(question, user, "aaa");
    }

    @Test
    public void 같은_유저_확인() {
        assertThat(answer.isSameUser(user)).isEqualTo(true);
    }

    @Test
    public void 같은_유저_확인2() {
        assertThat(answer.isSameUser(user2)).isEqualTo(false);
    }

    @Test
    public void 답변삭제_세션값이_null값일때() {
        Result result = answer.deleted(null);
        assertThat(result.isValid()).isEqualTo(false);
        log.debug(result.getErrorMessage());  // 로그인 필요 메시지 출력
    }

    @Test
    public void 답변삭제_답변자가_아닐때() {
        Result result = answer.deleted(user2);
        assertThat(result.isValid()).isEqualTo(false);
        log.debug(result.getErrorMessage());  // 다른 사람 정보 접근 불가 에러 메시지 출력
    }

    @Test(expected = NullPointerException.class)
    public void 답변이_null값일때() {
        Result result = ((Answer)null).deleted(user);  // 이에 대한 에러처리 필요
    }
}