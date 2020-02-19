package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QnaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {

  private static Logger log = LoggerFactory.getLogger(QnaController.class);

  @Autowired
  private QnaRepository qnaRepository;

  @PostMapping(value = "/questions")
  public String postQuestion(@ModelAttribute Question question) {
    qnaRepository.save(question);
    return "redirect:/";
  }

  @GetMapping(value = "/questions/{index}")
  public String getQuestion(@PathVariable("index") long index, Model model) {
    model.addAttribute("qna", qnaRepository.findById(index).get());
    return "/qna/show";
  }

  @GetMapping(value = "/")
  public String redirectToQnaList(Model model) {
    model.addAttribute("qnas", qnaRepository.findAll());
    return "index";
  }
}
