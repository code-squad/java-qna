package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class QuestionController {
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
            System.out.println(e.getMessage());
            return "redirect:/error/db";
        }
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute("question", questionRepo.findById(id).get());
        return "/question/show";
    }
}
