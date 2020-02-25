package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
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

  @GetMapping(value = "/form")
  public String form(HttpSession httpSession) {
    if (!HttpSessionUtils.isLoginUser(httpSession)) {
      return "/user/login";
    }
    return "/qna/form";
  }

  @PostMapping(value = "")
  public String create(String title, String contents, HttpSession httpSession) {
    if(!HttpSessionUtils.isLoginUser(httpSession)) {
      return "/user/login";
    }
    User sessionUser = (User) httpSession.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
    Question question = new Question(sessionUser.getUserId(), title, contents);
    qnaRepository.save(question);
    return "redirect:/";
  }

  @GetMapping(value = "/{index}")
  public String getQuestion(@PathVariable("index") long id, Model model) {
    model.addAttribute("question", qnaRepository.getOne(id));
    return "/qna/show";
  }

  @GetMapping(value = "")
  public String getQuestions(Model model) {
    model.addAttribute("questions", qnaRepository.findAll());
    return "index";
  }
}
