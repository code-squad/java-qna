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
    private int firstPage = 1;
    private int lastPage = 5;

    @GetMapping("/")
    public String viewWelcomePage(Model model) {
        Page page = initPage();
        this.totalPages = page.getTotalPages();
        List<PageWrapper> pageWrappers = createPages(this.totalPages);
        List<PageWrapper> partPageWrappers = new ArrayList<>();
        for (int count = firstPage; count <= lastPage; count++) {
            for (PageWrapper each : pageWrappers) {
                if (each.getIndex() == count) partPageWrappers.add(each);
            }
        }
        model.addAttribute("pageWrappers", partPageWrappers);
        model.addAttribute("questions", pageWrappers.get(INITIAL_PAGE_NUMBER).getPage());
        return "/index";
    }

    @GetMapping("/{index}")
    public String viewQuestionList(@PathVariable int index, Model model) {
        Page page = initPage();
        List<PageWrapper> pageWrappers = createPages(page.getTotalPages());
        model.addAttribute("pageWrappers", pageWrappers.subList(firstPage, lastPage));
        model.addAttribute("questions", pageWrappers.get(index - 1).getPage());
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
