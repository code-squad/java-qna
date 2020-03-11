package com.codessquad.qna.answer;

import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.errors.QuestionException;
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
   * Feat : Answer 를 작성합니다.
   * Desc : getSessionedUser() 를 통해 로그인되지 않은 사용자일 경우 에러가 발생합니다.
   * Return : /questions/ + questionId
   */
  @PostMapping("")
  public Answer createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
    User sessionedUser = getSessionedUserOrError(session);
    Question question = getQuestionOrError(questionRepository, questionId);

    answer.setUser(sessionedUser);
    answer.setQuestion(question);
    answerRepository.save(answer);

    //    return "redirect:/questions/" + questionId;
    return answer;
  }

  /**
   * Feat : Answer 을 delete 합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = getSessionedUserOrError(session);
    Answer answer = getAnswerOrError(answerRepository, id);

    if (sessionedUser.equals(answer.getUser())) {
      answer.delete();
      answerRepository.save(answer);
      return "redirect:/questions/" + questionId;
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }
}
