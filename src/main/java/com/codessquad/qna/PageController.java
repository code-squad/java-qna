package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PageController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/test1")
    public Page viewQuestionList() {
        List<Page> pages = createPages();
        return pages.get(0);
    }

    private List<Page> createPages() {
        int lastPage = 3;
        List<Page> pages = new ArrayList<>();
        PageRequest pageRequest;

        for (int i = 0; i < lastPage; i++) {
            pageRequest = PageRequest.of(i, 5);
            pages.add(questionRepository.findAll(pageRequest));
        }
        return pages;
    }
}
