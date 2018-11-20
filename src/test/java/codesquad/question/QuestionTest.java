package codesquad.question;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    Question question1;
    Question question2;

    @Before
    public void setUp() throws Exception {
        question1 = new Question(new Long(1), "브래드", "질문있습니다1", "예전에는 매번 id/pwd를 서버에서 보내 클라이언트 브라우저가 가지고 있다가(쿠키- http 상태값) 요청합니다. 그런데 보안적으로 id/pwd가 요청되는게 좋지 않겠죠.", new Long(1), "2018-11-02 13:12");
        question2 = new Question(new Long(2), "이정현", "질문있습니다2", "하하하하하", new Long(2), "2018-11-02 13:12");
    }

    @Test
    public void matchId() {
        assertThat(question1.matchId(new Long(1))).isEqualTo(true);
    }

    @Test
    public void update() {
        question1.update(question2);
        assertThat(question1.getTitle()).isEqualTo(question2.getTitle());
        assertThat(question1.getContents()).isEqualTo(question2.getContents());
    }
}