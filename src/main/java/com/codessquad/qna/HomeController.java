package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private QuestionRepository questionRepository;

    private static final int INITIAL_PAGE_NUMBER = 0;
    private static final int DEFAULT_FIRST_PAGE = 1;
    private static final int DEFAULT_LAST_PAGE = 6;
    PageUtils pageUtils = new PageUtils(DEFAULT_FIRST_PAGE, DEFAULT_LAST_PAGE);

//    @GetMapping("/")
//    public String viewWelcomePage(Model model) {
//        pageUtils.initPage(questionRepository);
//        List<Page> pages = pageUtils.createPages(questionRepository);
//        List<Page> subPages = pageUtils.getSubPages(questionRepository);
//
//        model.addAttribute("pages", subPages);
//        model.addAttribute("questions", pages.get(INITIAL_PAGE_NUMBER));
//        return "/index";
//    }
}
