package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QnaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

  @Autowired
  private QnaRepository qnaRepository;

  @GetMapping(value = "/{page}")
  public String index(Model model, @PathVariable("page") Optional<Integer> page) {
    int pageNumber = page.isPresent() ? page.get() : 0;
    Page<Question> questions = qnaRepository.findAll(PageRequest.of(0, pageNumber));
    model.addAttribute("questions", questions.getContent());
    return "index";
  }

}
