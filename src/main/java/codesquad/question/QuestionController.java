package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private QuestionRepository questions = QuestionRepository.getInstance();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questions.getAll());
        return "index";
    }

    @PostMapping
    public String create(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }

    @GetMapping("/{index}")
    public String show(Model model, @PathVariable int index) {
        Question question = questions.get(index);
        model.addAttribute("question", question);
        return "/qna/show";
    }
}
