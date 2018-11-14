package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static codesquad.qna.QuestionRepository.getQuestionRepository;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("questions", getQuestionRepository().getQuestions());
        return "index";
    }
}
