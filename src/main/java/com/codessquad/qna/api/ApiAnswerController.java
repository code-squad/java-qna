package com.codessquad.qna.api;

import com.codessquad.qna.answer.Answer;
import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.commons.CustomMessage;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.commons.CommonUtils.*;

@Slf4j
@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

  @Autowired
  AnswerRepository answerRepository;
  @Autowired
  QuestionRepository questionRepository;

  /**
   * Feat : Answer 를 create 합니다.
   * Desc :
   * Return : create 된 Answer
   */
  @PostMapping("")
  public Answer createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
    User sessionedUser = getSessionedUserOrError(session);
    Question question = getQuestionOrError(questionRepository, questionId);

    answer.setUser(sessionedUser);
    answer.setQuestion(question);
    answerRepository.save(answer);

    return answer;
  }

  /**
   * Feat : Answer 를 delete 합니다.
   * Desc : sessionedUser 와 answer 의 작성자가 다른 경우 에러를 발생시킵니다.
   * Return : /questions/show
   */
  @DeleteMapping("/{id}")
  public Result delete(@PathVariable Long id, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = getSessionedUserOrError(session);
    Answer answer = getAnswerOrError(answerRepository, id);

    if (sessionedUser.equals(answer.getUser())) {
      answer.delete();
      answerRepository.save(answer);
      return Result.ok();
    }

    return Result.fail(CustomMessage.USER_NOT_MATCHED);
  }
}
