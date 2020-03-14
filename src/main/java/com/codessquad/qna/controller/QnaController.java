package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.error.Result;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/questions")
public class QnaController {

  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @Autowired
  private QnaRepository qnaRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @GetMapping(value = "/form")
  public String form(HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "/user/login";
    }
    return "/qna/form";
  }

  @PutMapping(value = "/{id}")
  public String update(@PathVariable("id") Long questionId, String title,
      String contents, Model model, HttpSession httpSession) {
    Question question = qnaRepository.getOne(questionId);
    Result result = valid(httpSession, question);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    question.update(title, contents);
    qnaRepository.save(question);
    return "redirect:/questions/{id}";
  }

  @DeleteMapping(value = "/{id}")
  public String delete(@PathVariable("id") Long questionId, Model model, HttpSession httpSession) {
    Question question = qnaRepository.getOne(questionId);
    Result result = valid(httpSession, question);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    qnaRepository.delete(question);
    return "redirect:/questions/";
  }

  @GetMapping(value = "/{id}/form")
  public String updateForm(@PathVariable("id") Long questionId, Model model,
      HttpSession httpSession) {
    Question question = qnaRepository.getOne(questionId);
    Result result = valid(httpSession, question);
    if (!result.isValid()) {
      model.addAttribute("errorMessage", result.getErrorMessage());
      return "/user/login";
    }
    model.addAttribute("question", question);
    return "/qna/update_form";
  }

  @PostMapping(value = "")
  public String create(String title, String contents, HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "/user/login";
    }

    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    Question question = new Question(sessionUser, title, contents);
    qnaRepository.save(question);
    return "redirect:/questions/";
  }

  @GetMapping(value = "/{id}")
  public String show(@PathVariable("id") long id, Model model) {
    model.addAttribute("question", qnaRepository.getOne(id));
    model.addAttribute("answers", answerRepository.findByQuestionId(id));
    return "/qna/show";
  }

  private Result valid(HttpSession httpSession, Question question) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return Result.fail("로그인이 필요합니다");
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    if (!question.isSameWriter(sessionUser)) {
      return Result.fail("자신이 쓴 질문만 수정 혹은 삭제할 수 있습니다.");
    }
    return Result.ok();
  }
}
