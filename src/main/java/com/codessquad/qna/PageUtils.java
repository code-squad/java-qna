package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PageUtils {
    private static final Integer PAGE_SIZE = 5;
    private int lastPage;
    private int pageCount;

    public List<Page> pages;

    @Autowired
    public QuestionRepository questionRepository;

    public PageUtils() {
        pages = new ArrayList<>();
        pageCount = 0;
        lastPage = 4;
    }

    public List<Page> createPages() {
        PageRequest pageRequest;
        for (int i = 0; i < lastPage; i++) {
            pageRequest = PageRequest.of(pageCount, PAGE_SIZE);
            this.pages.add(questionRepository.findAll(pageRequest));
        }
        return pages;
    }

    public List<Page> createPages2() {
        int lastPage = 3;
        List<Page> pages = new ArrayList<>();
        PageRequest pageRequest;

        for (int pageNumber = 0; pageNumber < lastPage; pageNumber++) {
            pageRequest = PageRequest.of(pageNumber, 5);
            pages.add(questionRepository.findAll(pageRequest));
        }
        return pages;
    }

    @GetMapping("/test")
    public List<Question> viewQuestionList() {
        int page = 1;
        int size = 4;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("title"));
        return questionRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/test2")
    public Page viewQuestionList2() {
        int page = 1;
        int size = 4;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("title"));
        return questionRepository.findAll(pageRequest);
    }

    @GetMapping("/test3")
    public Page viewQuestionList3() {
        List<Page> pages = createPages();
        return pages.get(0);
    }

    @GetMapping("/test4")
    public Page viewQuestionList4() {
        PageUtils pageUtils = new PageUtils();
        List<Page> pages = pageUtils.createPages();
        return pages.get(0);
    }
}
