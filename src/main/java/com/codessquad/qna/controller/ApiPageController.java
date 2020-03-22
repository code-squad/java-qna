package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/questions")
public class ApiPageController {
    @Autowired
    private QuestionRepository questionRepository;
    private final int LIMIT_OF_PAGE = 15;

    @GetMapping
    public Page<Question> getCurrentPageQuestion(@RequestParam(defaultValue = "1") int page) {
        return questionRepository.findAllByDeletedFalse(
                PageRequest.of(page-1, LIMIT_OF_PAGE, Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
