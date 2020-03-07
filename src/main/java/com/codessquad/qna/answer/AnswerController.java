package com.codessquad.qna.answer;

import com.codessquad.qna.commons.CommonUtils;
import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRepository;
import com.codessquad.qna.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

  @PostMapping("")
  public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
    User sessionedUser = CommonUtils.getSessionedUser(session);
    Question question = CommonUtils.getQuestion(questionRepository, questionId);

    answer.setUserId(sessionedUser.getUserId());
    answer.setQuestion(question);
    answerRepository.save(answer);

    return "redirect:/questions/" + questionId;
  }
}
