package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        return "/index";
    }

    @GetMapping("/{number}")
    public String viewQuestionList(Model model, @PathVariable int number) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());

        model.addAttribute("pages", pages);
        model.addAttribute("questions", pages.get(number));

        return "/index";
    }

    public Page initPage() {
        int index = 0;
        return createPage(index);
    }

    public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, 4);
        Page page = questionRepository.findAll(pageRequest);
        return page;
    }

    public List<Page> createPages(int totalPages) {
        Page page;
        List<Page> pages = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            page = createPage(i);
            pages.add(page);
        }
        return pages;
    }
}

