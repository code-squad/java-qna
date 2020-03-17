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
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;
    private List<Page> pages;
    private static final int QUESTIONS_OF_EACHPAGE = 15;

    @GetMapping("/")
    public String index(Model model) {
        pages = createPages();
        model.addAttribute("questions", pages.get(0));
        model.addAttribute("pages", pages);
        return "index";
    }


    private List<Page> createPages() {
        int numberOfQuestions = questionRepository.findAll().size();
        int lastPage = (numberOfQuestions / QUESTIONS_OF_EACHPAGE) + 1;
        pages = new ArrayList<>();
        PageRequest pageRequest;

        for (int i = 0; i < lastPage; i++) {
            pageRequest = PageRequest.of(i, 5);
            pages.add(questionRepository.findAll(pageRequest));
        }
        return pages;
    }
}
