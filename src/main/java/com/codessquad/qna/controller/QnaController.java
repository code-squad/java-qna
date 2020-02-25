package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.web.HttpSessionUtils;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping(value = "")
  public String create(String title, String contents, HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "/user/login";
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    Question question = new Question(sessionUser, title, contents);
    qnaRepository.save(question);
    return "redirect:/";
  }

  @GetMapping(value = "/{id}")
  public String getQuestion(@PathVariable("id") long id, Model model) {
    model.addAttribute("question", qnaRepository.getOne(id));
    model.addAttribute("answers", answerRepository.findByQuestionId(id));
    return "/qna/show";
  }

  @GetMapping(value = "")
  public String getQuestions(Model model) {
    model.addAttribute("questions", qnaRepository.findAll());
    return "index";
  }
}
