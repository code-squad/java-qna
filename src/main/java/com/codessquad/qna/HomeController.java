package com.codessquad.qna;

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
    @Autowired
    private QuestionRepository questionRepository;

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int QUESTIONS_OF_PAGE = 2;
    private static final int PAGES_ON_DISPLAY = 5;
    private int firstPage = 1;
    private int lastPage = 6;
    private int totalPage = 0;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", subList(pages, firstPage, lastPage));//pages.subList(firstPage, lastPage));
        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
        return "/index";
    }

    @GetMapping("/{pageNumber}") // ?key=
    public String viewQuestionList(@PathVariable int pageNumber, Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", subList(pages, firstPage, lastPage));//pages.subList(firstPage, lastPage));
        log.info("pageNumber : {}", pageNumber);
        log.info("firstPagef : {}", firstPage);
        log.info("lastPagef : {}", lastPage);
        log.info("totalPagef : {}", totalPage);
        model.addAttribute("questions", pages.get(pageNumber - 1));
        return "/index";
    }

    @GetMapping("/moveNext")
    public String moveNextPage() {
        plusPageCount();
        return "redirect:/" + firstPage;
    }

    @GetMapping("/movePrev")
    public String movePrevPage() {
        minusPageCount();
        return "redirect:/" + firstPage;
    }

    public void plusPageCount() {
        this.firstPage += 5;
        this.lastPage += 5;

        if (totalPage < firstPage) {
            firstPage -= 5;
        }
        if (totalPage < lastPage) {
            lastPage = totalPage + 1;
        }
    }

    public void minusPageCount() {
        if (totalPage == lastPage - 1) {
            lastPage += (5 - (lastPage - firstPage));
        }

        this.firstPage -= 5;
        this.lastPage -= 5;

        if (firstPage < INITIAL_PAGE_NUMBER) {
            firstPage = INITIAL_PAGE_NUMBER + 1;
        }
        if (totalPage > 5 && lastPage < 5) {
            lastPage = 5 + 1;
        }
    }

    public Page initPage() {
        Page page = createPage(INITIAL_PAGE_NUMBER);
        this.totalPage = page.getTotalPages();
        return page;
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

    public List<Page> subList(List<Page> intputPages, int firstPage, int lastPage) {
        List<Page> pages = intputPages.subList(firstPage, lastPage - 1);
        pages.add(createPage(lastPage - 1));
        return pages;
    }
}
