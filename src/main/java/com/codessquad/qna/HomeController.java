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
    private static final int QUESTIONS_OF_PAGE = 6;
    private static final int LIMIT_OF_PAGES = 5;
    private int firstPage = 1;
    private int lastPage = 6;
    private int totalPage = 0;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", getSubPages(pages, firstPage, lastPage));
        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
        return "/index";
    }

    @GetMapping("/{pageNumber}") // ?key=
    public String viewQuestionList(@PathVariable int pageNumber, Model model) {
        Page page = initPage();
        List<Page> pages = createPages(page.getTotalPages());
        model.addAttribute("pages", getSubPages(pages, firstPage, lastPage));
        log.info("pageNumber : {}", pageNumber);
        log.info("firstPage : {}", firstPage);
        log.info("lastPage : {}", lastPage);
        log.info("totalPage : {}", totalPage);
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
        this.firstPage += LIMIT_OF_PAGES;
        this.lastPage += LIMIT_OF_PAGES;

        if (totalPage < firstPage) {
            firstPage -= LIMIT_OF_PAGES;
        }
        if (totalPage < lastPage) {
            lastPage = totalPage + 1;
        }
    }

    public void minusPageCount() {
        if (totalPage == lastPage - 1) {
            lastPage += (LIMIT_OF_PAGES - (lastPage - firstPage));
        }
        this.firstPage -= LIMIT_OF_PAGES;
        this.lastPage -= LIMIT_OF_PAGES;

        if (firstPage < INITIAL_PAGE_NUMBER) {
            firstPage = INITIAL_PAGE_NUMBER + 1;
        }
        if (totalPage > LIMIT_OF_PAGES && lastPage < LIMIT_OF_PAGES) {
            lastPage = LIMIT_OF_PAGES + 1;
        }
    }

    public Page initPage() {
        Page page = createPage(INITIAL_PAGE_NUMBER);
        this.totalPage = page.getTotalPages();

        // 페이지 갯수가 5보다 작을 경우 이니셜값 변경
        if (totalPage < LIMIT_OF_PAGES) {
            lastPage = totalPage + 1;
        }
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

    public List<Page> getSubPages(List<Page> inputPages, int firstPage, int lastPage) {
        // 마지막에만 페이지 추가
        if (totalPage < lastPage) {
            List<Page> pages = inputPages.subList(firstPage, lastPage - 1);
            pages.add(createPage(lastPage - 1));
            return pages;
        }
        List<Page> pages = inputPages.subList(firstPage, lastPage);
        return pages;
    }
}
