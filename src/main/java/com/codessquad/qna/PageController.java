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
public class PageController {
    private Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private QuestionRepository questionRepository;
    private int totalPages = 0;
    private int firstPageNumber = 1;
    private int lastPageNumber = 5;
    private static final int INITIAL_NUMBER = 0;
    private static final int HOMEPAGE_NUMBER = 1;
    private static final int QUESTIONS_OF_PAGE = 3;
    private static final int COUNT_OF_FOOTER_PAGE_NUMBERS = 5;

    @GetMapping("/")
    public String index(Model model) {
        getTotalPages();
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> footerPageNumbers = getHomePageFooterPageNumbers(pageWrappers);
        model.addAttribute("pageWrappers", footerPageNumbers);
        model.addAttribute("questions", getCurrentPage(pageWrappers, HOMEPAGE_NUMBER));
        model.addAttribute("next", isLastPageGroup());
        model.addAttribute("prev", isFirstPageGroup());
        return "/index";
    }

    @GetMapping("/{index}")
    public String showPage(@PathVariable int index, Model model) {
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> footerPageNumbers = getFooterPageNumbers(pageWrappers);
        model.addAttribute("pageWrappers", footerPageNumbers);
        model.addAttribute("questions", getCurrentPage(pageWrappers, index));
        model.addAttribute("next", isLastPageGroup());
        model.addAttribute("prev", isFirstPageGroup());
        return "/index";
    }

    public void getTotalPages() {
        Page page = createInitialPage();
        this.totalPages = page.getTotalPages();
    }

    private Next isLastPageGroup() {
        if (this.totalPages > lastPageNumber) return new Next();
        return null;
    }

    private Prev isFirstPageGroup() {
        if (this.firstPageNumber != 1) return new Prev();
        return null;
    }

    private List<PageWrapper> getFooterPageNumbers(List<PageWrapper> pageWrappers) {
        List<PageWrapper> partPageWrappers = new ArrayList<>();
        for (int count = firstPageNumber; count <= lastPageNumber; count++) {
            for (PageWrapper each : pageWrappers) {
                if (each.getIndex() == count) partPageWrappers.add(each);
            }
        }
        return partPageWrappers;
    }

    private List<PageWrapper> getHomePageFooterPageNumbers(List<PageWrapper> pageWrappers) {
        List<PageWrapper> partPageWrappers = new ArrayList<>();
        for (int count = 1; count <= 5; count++) {
            for (PageWrapper each : pageWrappers) {
                if (each.getIndex() == count) partPageWrappers.add(each);
            }
        }
        return partPageWrappers;
    }

    public Page getCurrentPage(List<PageWrapper> pageWrappers, int index) {
        for (PageWrapper each : pageWrappers) {
            if (each.getIndex() == index) return each.getPage();
        }
        return null;
    }

    @GetMapping("/moveNext")
    public String moveNext() {
        this.firstPageNumber += COUNT_OF_FOOTER_PAGE_NUMBERS;
        this.lastPageNumber += COUNT_OF_FOOTER_PAGE_NUMBERS;
        if (this.lastPageNumber > this.totalPages) {
            this.lastPageNumber = this.totalPages;
        }
        return "redirect:/" + firstPageNumber;
    }

    @GetMapping("/movePrev")
    public String movePrev() {
        this.firstPageNumber -= COUNT_OF_FOOTER_PAGE_NUMBERS;
        this.lastPageNumber -= COUNT_OF_FOOTER_PAGE_NUMBERS;
        if (lastPageNumber - firstPageNumber != 4) {
            lastPageNumber = firstPageNumber + 4;
        }
        return "redirect:/" + firstPageNumber;
    }

    public Page createInitialPage() {
        return createPage(INITIAL_NUMBER);
    }

    public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, QUESTIONS_OF_PAGE);
        Page page = questionRepository.findAllByDeletedFalse(pageRequest);
        return page;
    }

    public List<PageWrapper> createPages(int totalPages) {
        Page page;
        List<PageWrapper> pageWrappers = new ArrayList<>();
        for (int i = 0; i < totalPages; i++) {
            page = createPage(i);
            pageWrappers.add(new PageWrapper(page, i + 1));
        }
        return pageWrappers;
    }
}
