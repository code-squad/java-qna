package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private QuestionRepository questionRepository;

    private static final int DEFAULT_FIRST_PAGE = 1;
    private static final int DEFAULT_LAST_PAGE = 6;
    PageUtils pageUtils = new PageUtils(DEFAULT_FIRST_PAGE, DEFAULT_LAST_PAGE);

    @GetMapping("/{pageNumber}")
    public String viewQuestionsOnEachPage(@PathVariable int pageNumber, Model model) {
        pageUtils.initPage(questionRepository);
        List<Page> pages = pageUtils.createPages(questionRepository);
        List<Page> subPages = pageUtils.getSubPages(questionRepository);

        model.addAttribute("pages", subPages);
        model.addAttribute("questions", pages.get(pageNumber - 1));
        return "/index";
    }

    @GetMapping("/moveNext")
    public String moveNextPage() {
        return "redirect:/" + pageUtils.plusPageCount();
    }

    @GetMapping("/movePrev")
    public String movePrePage() {
        return "redirect:/" + pageUtils.minusPageCount();
    }
}
