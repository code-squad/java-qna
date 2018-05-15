package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String questions(Question question) {
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String viewDetail(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findOne(id));

        return "qna/show";
    }

    @GetMapping({"/", "/index"})
    public String welcome(Model model) {
        logger.debug("hello logback !");

        model.addAttribute("posts", questionRepository.findAll(new Sort(Sort.Direction.DESC, "id")));

        return "index";
    }
}