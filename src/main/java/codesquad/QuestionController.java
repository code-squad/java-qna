package codesquad;

import codesquad.model.Question;
import codesquad.model.Questions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private final Questions questions = new Questions();

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/submit")
    public String submit(Question question) {
        questions.addQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/question/{index}")
    public String showQuestion(@PathVariable String index, Model model) {
        try {
            Question question = questions.getQuestion(index);
            model.addAttribute("question", question);
            return "questions/show";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "index";
        }
    }
}
