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

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int QUESTIONS_OF_PAGE = 1;
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;
    private int totalPages = 0;
    private int firstPage = 1;
    private int lastPage = 5;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        this.totalPages = page.getTotalPages();
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> footerPageNumbers = getFooterPageNumbers(pageWrappers);
        model.addAttribute("pageWrappers", footerPageNumbers);
        model.addAttribute("questions", getCurrentPage(pageWrappers, 1));

        Next next = new Next();
        if (this.totalPages > lastPage) {
            model.addAttribute("next", next);
        }

        Prev prev = new Prev();
        if (this.firstPage != 1) {
            model.addAttribute("prev", prev);
        }
        return "/index";
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

    public Page initPage() {
        return createPage(INITIAL_PAGE_NUMBER);
    }

    public Page createPage(int index) {
        PageRequest pageRequest = PageRequest.of(index, QUESTIONS_OF_PAGE);
//        Page page = questionRepository.findAll(pageRequest);
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
