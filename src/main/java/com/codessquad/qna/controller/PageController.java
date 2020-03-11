package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QnaRepository;
import com.codessquad.qna.web.PageUtil;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  private static Logger log = LoggerFactory.getLogger(PageController.class);
  private static final int PAGE_SIZE = 4;

  @Autowired
  private QnaRepository qnaRepository;

  @GetMapping(value = "/")
  public String index(Model model, Optional<Integer> page) {
    int pageNumber = page.isPresent() ? page.get() - 1 : 0;

    Page<Question> questions = qnaRepository
        .findAll(PageRequest.of(pageNumber, PAGE_SIZE, Direction.DESC, "id"));

    int current = questions.getNumber() + 1;
    int totalPages = questions.getTotalPages();
    int index = questions.getNumber();

    model.addAttribute("questions", questions.getContent());
    model.addAttribute("pageUtil",
        new PageUtil(current, index, totalPages));
    return "index";
  }

}
