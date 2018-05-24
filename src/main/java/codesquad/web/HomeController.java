package codesquad.web;

import codesquad.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private QuestionRepository questionsRepository;

    @GetMapping("/")
    public String questionList(Model model) {
        log.info("홈에 들어옴");

        model.addAttribute("questions", questionsRepository.findAll());
        //log.debug(questionsRepository.toString());
        log.debug(questionsRepository.findAll().toString());

        return "index";
    }
}
