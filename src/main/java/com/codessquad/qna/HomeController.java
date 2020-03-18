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
    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int QUESTIONS_OF_EACH_PAGE = 1;
    private static final int SIZE_OF_PAGE_BAR = 5;
    private int firstPage = 1;
    private int lastPage = 6;
    private int totalPage = 0;
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        initPage();
        List<Page> pages = createPages(totalPage);
        List<Page> subPages = isLastPageBar() ? getLastSubPages(pages) : getSubPages(pages);
        model.addAttribute("pages", subPages);
        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
        return "/index";
    }

    @GetMapping("/{pageNumber}") // ?key=
    public String viewQuestionList(@PathVariable int pageNumber, Model model) {
        initPage();
        List<Page> pages = createPages(totalPage);
        List<Page> subPages = isLastPageBar() ? getLastSubPages(pages) : getSubPages(pages);
        model.addAttribute("pages", subPages);
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

    public void initPage() {
        Page page = createPage(INITIAL_PAGE_NUMBER);
        totalPage = page.getTotalPages();
        if (totalPage < SIZE_OF_PAGE_BAR) {
            lastPage = totalPage + 1;
        }
    }

    public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, QUESTIONS_OF_EACH_PAGE);
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

    public boolean isLastPageBar() {
        return lastPage > totalPage;
    }

    public List<Page> getSubPages(List<Page> inputPages) {
        return inputPages.subList(firstPage, lastPage);
    }

    public List<Page> getLastSubPages(List<Page> inputPages) {
        List<Page> pages = inputPages.subList(firstPage, lastPage - 1);
        pages.add(createPage(lastPage - 1));
        return pages;
    }

    public void plusPageCount() {
        firstPage += SIZE_OF_PAGE_BAR;
        lastPage += SIZE_OF_PAGE_BAR;

        if (firstPage > totalPage) {
            firstPage -= SIZE_OF_PAGE_BAR;
        }
        if (lastPage > totalPage) {
            lastPage = totalPage + 1;
        }
    }

    public void minusPageCount() {
        if (totalPage == lastPage - 1) {
            lastPage += (SIZE_OF_PAGE_BAR - (lastPage - firstPage));
        }
        firstPage -= SIZE_OF_PAGE_BAR;
        lastPage -= SIZE_OF_PAGE_BAR;

        if (firstPage < INITIAL_PAGE_NUMBER) {
            firstPage = INITIAL_PAGE_NUMBER + 1;
        }
        if (totalPage > SIZE_OF_PAGE_BAR && lastPage < SIZE_OF_PAGE_BAR) {
            lastPage = SIZE_OF_PAGE_BAR + 1;
        }
    }
}
