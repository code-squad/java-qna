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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {

  private static List<Question> questions = new ArrayList<>();
  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @PostMapping(value = "/questions")
  public String postQuestion(@ModelAttribute Question question) {
    question.setIndex(questions.size() + 1);
//    log.info(question.toString());
    questions.add(question);
    return "redirect:/";
  }

  @GetMapping(value = "/questions/{index}")
  public String getQuestion(@PathVariable("index") int index, Model model) {
    Question question = questions.get(index - 1);
//    log.info(question.toString());
    model.addAttribute("qna", question);
    return "/qna/show";
  }

  @GetMapping(value = "/")
  public String redirectToQnaList(Model model) {
    model.addAttribute("qnas", questions);
    return "index";
  }
}
