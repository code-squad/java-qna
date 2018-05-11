package codesquad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questions = new ArrayList<>();

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/question")
    public String question() {
        return "/qna/form";
    }

    @PostMapping("/submit")
    public String submit(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }
}
