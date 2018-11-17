package codesquad;

import codesquad.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String list(Model model) {
        System.out.println("첫화면");
        model.addAttribute("questions",questionRepository.findAll());
        return "index";
    }
}

