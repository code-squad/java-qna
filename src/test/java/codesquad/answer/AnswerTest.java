package codesquad.answer;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class AnswerTest {
    Answer answer;

    @Before
    public void setUp() throws Exception {
        answer = new Answer();
    }

    @Test
    public void 답변_삭제() {
        answer.delete();
        assertThat(answer.isDeleted()).isEqualTo(true);
    }
}