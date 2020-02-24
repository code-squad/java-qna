package com.codessquad.qna;

import com.codessquad.qna.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * 모든 Question 을 가져와서 welcome 페이지에 보여줍니다.
   */
  @GetMapping(value = {"", "/welcome"})
  public String goIndex(Model model) {
    model.addAttribute("questions", questionRepository.findAll());
    return "/welcome";
  }
}
