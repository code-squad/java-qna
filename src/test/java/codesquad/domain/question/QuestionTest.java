package codesquad.domain.question;

import codesquad.domain.answer.Answer;
import codesquad.domain.answer.Answers;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import codesquad.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuestionTest {
    private Question question;
    private Question updateInfo;
    private User user;
    private Optional<User> sessionUser;
    private Answer answer;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1L);
        question = new Question();
        question.setUser(user);
        question.setId(1L);
        answer = new Answer();
        answer.setId(1L);
        answer.setQuestion(question);

        updateInfo = new Question();
        updateInfo.setId(1L);
        sessionUser = Optional.of(new User());
        sessionUser.get().setId(1L);
    }

    @Test
    public void update() {
        sessionUser.get().setId(1L);
        question.update(sessionUser, updateInfo);
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_update() {
        sessionUser.get().setId(2L);
        question.update(sessionUser, updateInfo);
    }

    @Test
    public void delete() {
        User answerUser = new User();
        answerUser.setId(1L);
        answer.setUser(answerUser);
        question.setAnswers((new Answers(answer)));
        question.delete(sessionUser, question.getId());
    }

    @Test(expected = UnAuthorizedException.class)
    public void invalid_delete_not_match_user() {
        User answerUser = new User();
        answerUser.setId(2L);
        answer.setUser(answerUser);
        question.setAnswers(new Answers(answer));
        question.delete(sessionUser, question.getId());
    }

    @Test(expected = ForbiddenRequestException.class)
    public void invalid_delete_already_delete() {
        User answerUser = new User();
        answerUser.setId(1L);
        answer.setUser(answerUser);
        question.setAnswers((new Answers(answer)));
        question.delete(sessionUser, question.getId());
        question.delete(sessionUser, question.getId());
    }
}