package codesquad.web;

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
    public String inputQuestion(Question question) {
        question.setIndex(questions.size() + 1);
        this.questions.add(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);

        return "index";
    }

    @GetMapping("/questions/{index}")
    public String questionDetail(@PathVariable int index, Model model) {
        model.addAttribute("question", questions.get(index - 1));
        return "/qna/show";
    }
}
