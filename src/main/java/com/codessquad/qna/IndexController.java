package com.codessquad.qna;

import com.codessquad.qna.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping(value = {"", "/index"})
  public String goIndex(Model model) {
    model.addAttribute("questions", questionRepository.findAll());
    return "/index";
  }
}
