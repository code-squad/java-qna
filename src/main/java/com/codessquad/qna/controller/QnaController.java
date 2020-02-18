package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {

  private static ArrayList<Question> qnaPosts = new ArrayList<>();
  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @PostMapping(value = "/questions")
  public String postQuestion(@ModelAttribute Question question) {
    log.info(question.toString());
    qnaPosts.add(question);
    return "redirect:/";
  }

}
