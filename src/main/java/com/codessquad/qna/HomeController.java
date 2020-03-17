package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;

    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int QUESTIONS_OF_PAGE = 1;
    private int totalPages = 0;
    private int firstPage = 0;
    private int lastPage = 5;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        this.totalPages = page.getTotalPages();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", pages.subList(firstPage, lastPage));
        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
        return "/index";
    }

    @GetMapping("/{pageNumber}")
    public String viewQuestionList(@PathVariable int pageNumber, Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", pages.subList(14, 16));
        model.addAttribute("questions", pages.get(pageNumber));
        return "/index";
    }

    @GetMapping("/moveNext")
    public String moveNext() {
        this.firstPage += 5;
        this.lastPage += 5;
        if (this.lastPage > this.totalPages) {
            this.lastPage = this.totalPages;

        }
        if (this.firstPage > this.totalPages) {
            this.firstPage -= 5;
            logger.info("firstPage : {}", firstPage);
        }

        return "redirect:/" + firstPage;
    }

    @GetMapping("/movePrev")
    public String movePrev() {
        this.firstPage -= 5;
        this.lastPage -= 5;
        if (firstPage < 1) {
            this.firstPage = INITIAL_PAGE_NUMBER;
        }
        if (totalPages > 5 && lastPage < 5) {
            lastPage = 5;
        }
        return "redirect:/" + firstPage;
    }

    public Page initPage() {
        return createPage(INITIAL_PAGE_NUMBER);
    }

    public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, QUESTIONS_OF_PAGE);
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
