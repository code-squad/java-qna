package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionRepository;
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

    private static final int INITIAL_PAGE_NUMBER = 0;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", pages);
        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
        return "/index";
    }

    @GetMapping("/{pageNumber}")
    public String viewQuestionList(@PathVariable int pageNumber, Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", pages);
        model.addAttribute("questions", pages.get(pageNumber));
        return "/index";
    }

    public Page initPage() {
        return createPage(INITIAL_PAGE_NUMBER);
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
