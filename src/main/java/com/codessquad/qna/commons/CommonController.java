package com.codessquad.qna.commons;

import com.codessquad.qna.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CommonController {

  private static final Logger log = LoggerFactory.getLogger(CommonController.class);

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * 모든 Question 을 가져와서 welcome 페이지에 보여줍니다.
   */
  @GetMapping(value = {"", "/welcome"})
  public String welcome(Model model) {
    log.debug("### welcome debug log message");
    log.info("### welcome info log message");

    model.addAttribute("questions", questionRepository.findAll());

    return "/welcome";
  }
}
