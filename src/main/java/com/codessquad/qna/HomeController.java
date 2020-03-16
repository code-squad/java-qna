package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String index(Model model) {
        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("createdDate").descending());
        Page<Question> pages = questionRepository.findAll(pageRequest);
        model.addAttribute("questions", questionRepository.findAll(pageRequest));
        model.addAttribute("countOfPages", pages.getContent());
        logger.info("pages.getContent : {}" , pages.getContent().size());
        return "index";
    }

}
