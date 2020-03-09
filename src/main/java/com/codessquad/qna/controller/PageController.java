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

  @Autowired
  private QnaRepository qnaRepository;

  @GetMapping(value = "/")
  public String index(Model model, Optional<Integer> page) {
    int pageNumber = page.isPresent() ? page.get() - 1 : 1;
    Page<Question> questions = qnaRepository
        .findAll(PageRequest.of(pageNumber, 5, Direction.DESC, "createdTime"));
    int current = questions.getNumber() + 1;
    int total = questions.getTotalPages();

    log.debug(
        "총 element 수 : {},"
            + " 전체 page 수 : {}, "
            + "페이지에 표시할 element 수 : {}, "
            + "현재 페이지 index : {}, "
            + "현재 페이지의 element 수 : {}",
        questions.getTotalElements(),
        questions.getTotalPages(),
        questions.getSize(),
        questions.getNumber(),
        questions.getNumberOfElements()
    );

    model.addAttribute("questions", questions.getContent());
    model.addAttribute("pageUtil",
        new PageUtil(current, total));
    return "index";
  }

}
