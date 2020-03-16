package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String viewQuestionList(Model model) {
        List<Page> pages = createPages();
        int pageNumber; // @PathValuable 로 인덱스 가져오기
        model.addAttribute("questions", pages.get(0));
        return "/index";
    }

    public List<Page> createPages() {
        int lastPage = 3;
        List<Page> pages = new ArrayList<>();
        PageRequest pageRequest;

        for (int pageNumber = 0; pageNumber < lastPage; pageNumber++) {
            pageRequest = PageRequest.of(pageNumber, 5);
            pages.add(questionRepository.findAll(pageRequest));
        }
        return pages;
    }
}

