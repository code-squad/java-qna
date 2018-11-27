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

    @GetMapping({"", "/", "index"})
    public String index(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "forward:/questions";
    }
}