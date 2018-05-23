package codesquad.controller;

import codesquad.domain.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionRepo.findByDeletedIsFalse());
        return "index";
    }
}
