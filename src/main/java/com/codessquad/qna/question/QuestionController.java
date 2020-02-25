package com.codessquad.qna.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

  private String returnRedirect = "redirect:/questions";
  private String returnView = "/questions";

  @Autowired
  private QuestionRepository questionRepository;

  /**
   * Question 작성을 위한 form 으로 이동합니다.
   */
  @GetMapping("/form")
  public String form(Model model) {
    return returnView + "/form";
  }

  /**
   * Question 추가 후 welcome 페이지로 이동합니다.
   */
  @PostMapping("")
  public String create(Question question) {
    questionRepository.save(question);

    return "redirect:/welcome";
  }

  /**
   * Question 의 상세 내용을 보여주는 show 페이지로 이동합니다.
   */
  @GetMapping("/{id}")
  public String show(@PathVariable long id, Model model) {
    model.addAttribute("question", questionRepository.findById(id).get());

    return returnView + "/show";
  }
}
