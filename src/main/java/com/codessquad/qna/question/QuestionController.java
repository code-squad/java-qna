package com.codessquad.qna.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class QuestionController {

  private String returnRedirectUrl = "redirect:/questions";
  private String returnForwardUrl = "/questions";

  @Autowired
  private QuestionRepository questionRepository;

  @PostMapping("")
  public String create(Question question) {
    questionRepository.save(question);

    return "redirect:/index";
  }

  @GetMapping("/form")
  public String form(Model model) {
    return returnForwardUrl + "/form";
  }

  @GetMapping("/{index}")
  public ModelAndView show(@PathVariable long index) {
    ModelAndView mav = new ModelAndView("/questions/show");
    mav.addObject("questions", questionRepository.findById(index).get());
    return mav;
  }
}
