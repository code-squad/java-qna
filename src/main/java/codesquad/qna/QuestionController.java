package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String questions(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String showQuestions(Model model, @PathVariable("index") int index) {
        model.addAttribute(questions.stream().filter(x -> x.getIndex() == index).findFirst().orElse(null));
        return "qna/show";
    }
}
