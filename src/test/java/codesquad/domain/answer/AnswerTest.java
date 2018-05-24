package codesquad.domain.answer;

import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.question.Question;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AnswerTest {
    private Answer answer;
    private Question question;
    private User answerUser;
    private User otherUser;

    @Before
    public void setUp() throws Exception {
        answerUser = new User();
        question = new Question();
        question.setId(1L);
        question.setUser(answerUser);

        otherUser = new User();
        otherUser.setId(2L);
        answer = new Answer();
        answer.setUser(answerUser);
    }

    @Test
    public void delete() {
        answer.delete(question);
        assertTrue(answer.isDeleted());
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_delete_not_match_user() {
        answer.setUser(otherUser);
        answer.delete(question);
    }

    @Test(expected = ForbiddenRequestException.class)
    public void invalid_delete_already_delete() {
        answer.delete(question);
        answer.delete(question);
    }
}