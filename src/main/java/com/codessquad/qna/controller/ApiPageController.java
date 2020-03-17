package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/page")
public class ApiPageController {
    @Autowired
    private QuestionRepository questionRepository;
    private final int LIMIT_OF_PAGE = 15;

    @GetMapping("/{pageNumber}")
    public Page<Question> getCurrentPageQuestion(@PathVariable int pageNumber) {
        return questionRepository.findAllByDeletedFalse(
                PageRequest.of(pageNumber-1, LIMIT_OF_PAGE, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
