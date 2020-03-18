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
    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int QUESTIONS_OF_PAGE = 1;

    @Autowired
    private QuestionRepository questionRepository;
    private int totalPages = 0;
    private int firstPage = 1;
    private int lastPage = 5;

    public void initTotalPages() {
        Page page = initPage();
        this.totalPages = page.getTotalPages();
    }

    @GetMapping("/{index}")
    public String viewQuestionList(@PathVariable int index, Model model) {
        initTotalPages();
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> footerPageNumbers = getFooterPageNumbers(pageWrappers);
        model.addAttribute("pageWrappers", footerPageNumbers);
        model.addAttribute("questions", getCurrentPage(pageWrappers, index));
        model.addAttribute("next", isLastPageGroup());
        model.addAttribute("prev", isFirstPageGroup());
        return "/index";
    }

    private Next isLastPageGroup() {
        if (this.totalPages > lastPage) return new Next();
        return null;
    }

    private Prev isFirstPageGroup() {
        if (this.firstPage != 1) return new Prev();
        return null;
    }

    private List<PageWrapper> getFooterPageNumbers(List<PageWrapper> pageWrappers) {
        List<PageWrapper> partPageWrappers = new ArrayList<>();
        for (int count = firstPage; count <= lastPage; count++) {
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
        if (lastPage - firstPage != 4) {
            lastPage = firstPage + 4;
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
