package com.codessquad.qna.answer;

import com.codessquad.qna.commons.CommonUtils;
import com.codessquad.qna.commons.CustomErrorCode;
import com.codessquad.qna.errors.QuestionException;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

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
  public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
    User sessionedUser = CommonUtils.getSessionedUser(session);
    Question question = CommonUtils.getQuestion(questionRepository, questionId);

    answer.setUserId(sessionedUser.getUserId());
    answer.setQuestion(question);
    answerRepository.save(answer);

    return "redirect:/questions/" + questionId;
  }

  /**
   * Feat : Answer 을 delete 합니다.
   * Desc : getQuestion() 을 통해 Question 존재 여부를 검증합니다.
   * Return : /questions/show
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
    log.info("### delete()");

    User sessionedUser = CommonUtils.getSessionedUser(session);
    Answer answer = CommonUtils.getAnswer(answerRepository, id);

    if (sessionedUser.validateUserId(answer.getUserId())) {
      answerRepository.delete(answer);
      return "redirect:/questions/" + questionId;
    }

    throw new QuestionException(CustomErrorCode.USER_NOT_MATCHED);
  }
}
