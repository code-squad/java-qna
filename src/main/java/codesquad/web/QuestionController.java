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
    List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String questions(Question question) {
        question.setId(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String viewDetail(@PathVariable String index, Model model) {
        model.addAttribute("question", questions.get(Integer.valueOf(index) - 1));
        return "qna/show";
    }

    @GetMapping({"/", "index"})
    public String welcome(Model model) {
        model.addAttribute("posts", questions);
        return "index";
    }
}
