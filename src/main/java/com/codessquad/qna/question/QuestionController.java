package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

  List<Question> questionList = new ArrayList<>();
  private String returnRedirectUrl = "redirect:/questions";
  private String returnForwardUrl = "/questions";


  @PostMapping("")
  public String createQuestion(Question question) {
    questionList.add(question);
    return "redirect:/index";
  }

  @GetMapping("/form")
  public String goForm(Model model) {
    return returnForwardUrl + "/form";
  }

  @GetMapping("/{index}")
  public String goPage(@PathVariable int index, Model model) {
    model.addAttribute("question", questionList.get(index - 1));
    return returnForwardUrl + "/show";
  }
}
