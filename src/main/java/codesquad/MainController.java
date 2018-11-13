package codesquad;

import codesquad.qna.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", QuestionRepository.INSTANCE);
        return "index";
    }

}
