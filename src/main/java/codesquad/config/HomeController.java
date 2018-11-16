package codesquad.config;

import codesquad.question.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectHome() {
        return "redirect:/questions";
    }
}
