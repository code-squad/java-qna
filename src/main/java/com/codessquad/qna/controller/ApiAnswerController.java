package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.error.Result;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/questions/{id}/answers")
public class ApiAnswerController {

  @Autowired
  AnswerRepository answerRepository;

  @Autowired
  QnaRepository qnaRepository;

  @Autowired
  UserRepository userRepository;


  @PostMapping(value = "")
  public Answer create(@PathVariable("id") Long questionsId, String contents,
      HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return null;
    }

    Question question = qnaRepository.getOne(questionsId);
    User writer = HttpSessionUtils.getUserFromSession(httpSession);
    Answer answer = new Answer(question, writer, contents);
    return answerRepository.save(answer);
  }

  @DeleteMapping(value = "/{id}")
  public String delete(@PathVariable("id") Long answerId, HttpSession httpSession,
      Model model, HttpServletRequest request) {
    Answer answer = answerRepository.getOne(answerId);
    Result result = valid(httpSession, answer);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }

    answerRepository.delete(answer);
    String referer = request.getHeader("Referer");
    return "redirect:" + referer;
  }

  private Result valid(HttpSession httpSession, Answer answer) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return Result.fail("로그인이 필요합니다");
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    if (!answer.isSameWriter(sessionUser)) {
      return Result.fail("자신이 쓴 답글만 수정 혹은 삭제할 수 있습니다.");
    }
    return Result.ok();
  }


}
