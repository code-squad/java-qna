package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

  List<Question> questionList = new ArrayList<>();

  @GetMapping("/qna/form")
  public String goForm(Model model) {
    return "qna/form";
  }

  @PostMapping("/question")
  public String createUser(Question question) {
    questionList.add(question);
    return "redirect:/index";
  }

  @GetMapping("/index")
  public String goIndex(Model model) {
    model.addAttribute("questions", questionList);
    return "index";
  }
}
