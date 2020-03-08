package com.codessquad.qna.commons;

import com.codessquad.qna.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonController {

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * Feat : 모든 Question 을 가져옵니다.
   * Desc : Get 처리를 위해 분리하였습니다.
   * Return : /welcome
   */
  @GetMapping(value = {"/", ""})
  public String welcomeGet(Model model) {
    model.addAttribute("questions", questionRepository.findAll());

    return "/welcome";
  }

  /**
   * Feat : 모든 Question 을 가져옵니다.
   * Desc : Post 처리를 위해 분리하였습니다.
   * Return : /welcome
   */
  @PostMapping("/welcome")
  public String welcomePost(Model model) {
    model.addAttribute("questions", questionRepository.findAll());

    return "/welcome";
  }
}
