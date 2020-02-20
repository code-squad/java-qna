package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping(value = {"", "/index"})
  public String goIndex(Model model) {
//    model.addAttribute("questions", questionList);
    return "/index";
  }
}
