package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionsRepository;

    @PostMapping("/questions")
    public String inputQuestion(Question question) {
        questionsRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        log.info("홈에 들어옴");

        model.addAttribute("questions", questionsRepository.findAll());

        return "index";
    }

    @GetMapping("/questions/{index}")
    public String questionDetail(@PathVariable int index, Model model) {
        Question question = questionsRepository.findOne(index);
        model.addAttribute("question", question);

        return "/qna/show";
    }
}
