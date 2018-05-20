package codesquad.controller;

import codesquad.domain.question.Question;
import codesquad.domain.question.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questionRepo.findAll(Sort.by(Sort.Order.desc("id"))));
        return "index";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        try {
            questionRepo.save(question);
            return "redirect:/";
        } catch (DataAccessException e) {
            logger.error("ERROR {} ", e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", questionRepo.findById(id).get());
        return "/question/show";
    }
}
