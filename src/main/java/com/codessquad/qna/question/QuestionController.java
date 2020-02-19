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
public class QuestionController {

  List<Question> questionList = new ArrayList<>();

  @GetMapping("/qna/form")
  public String goForm(Model model) {
    return "/qna/form";
  }

  @PostMapping("/question")
  public String createQuestion(Question question) {
    questionList.add(question);
    return "redirect:/index";
  }

  @GetMapping(value = {"/", "/index"})
  public String goIndex(Model model) {
    model.addAttribute("questions", questionList);
    return "/index";
  }

  @GetMapping("/questions/{index}")
  public String goPage(@PathVariable int index, Model model) {
    model.addAttribute("question", questionList.get(index - 1));
    return "/qna/show";
  }
}
