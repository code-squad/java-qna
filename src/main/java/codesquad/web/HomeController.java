package codesquad.web;

import codesquad.domain.PageUtils;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String goHome(Model model) {
        Page<Question> questions = questionRepository.findAll(new PageRequest(0, PageUtils.COUNT_OF_QUESTIONS_IN_PAGE));
        int countOfQuestion = (int)questionRepository.count();

        model.addAttribute("questions", questions);
        model.addAttribute("pages", PageUtils.createButton(countOfQuestion, 0));
        log.debug("Count : {}", countOfQuestion);
        log.debug("Pages : {}", PageUtils.createButton(countOfQuestion, 0));
        return "index";
    }

    @GetMapping("/{page}")
    public String showPage(@PathVariable int page, Model model){
        Page<Question> questions = questionRepository.findAll(new PageRequest(page, PageUtils.COUNT_OF_QUESTIONS_IN_PAGE));
        int countOfQuestion = (int)questionRepository.count();

        model.addAttribute("questions", questions);
        model.addAttribute("pages", PageUtils.createButton(countOfQuestion, page));
        log.debug("Count : {}", countOfQuestion);
        log.debug("Pages : {}", PageUtils.createButton(countOfQuestion, page));
        return "index";
    }
}
