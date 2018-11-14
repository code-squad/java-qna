package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @PostMapping("")
    public String questions(Question question) {
        QuestionRepository.getInstance().addQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String showQuestions(Model model, @PathVariable("index") int index) {
        model.addAttribute(
                QuestionRepository.getInstance().getQuestions()
                        .stream().filter(x -> x.getIndex() == index).findFirst().orElse(null));
        return "qna/show";
    }
}
