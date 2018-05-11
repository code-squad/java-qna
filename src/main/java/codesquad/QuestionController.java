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

    @GetMapping("/question")
    public String question(Model model) {
        model.addAttribute("questions", questions);
        System.out.println(questions.size());
        return "/qna/form";
    }

    @PostMapping("/submit")
    public String submit(Question question) {
        questions.add(question);
        return "redirect:/";
    }
}
