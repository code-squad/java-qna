package codesquad;

import codesquad.question.Question;
import codesquad.question.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

//    @PostMapping("/")
//    public String refreshQuestionLogs(Model model) {
//        for (Question question: QuestionRepository.getUsers()) {
//            model.addAttribute("question", question);
//        }
//        return "redirect:/";
//    }
}
