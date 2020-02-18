package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QnaController {

  private static List<Question> qnas = new ArrayList<>();
  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @PostMapping(value = "/questions")
  public String postQuestion(@ModelAttribute Question question) {
    log.info(question.toString());
    qnas.add(question);
    return "redirect:/";
  }

  @GetMapping(value = "/")
  public String redirectToQnaList(Model model) {
    model.addAttribute("qnas", qnas);
    return "index";
  }
}
