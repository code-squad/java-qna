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
    private Questions questions = Questions.of();

    @PostMapping
    public String create(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questions.getQuestions());
        return "/index";
    }

    @GetMapping("/{index}")
    public String show(Model model, @PathVariable int index) {
        model.addAttribute("question", matchQuestion(index));
        return "/qna/show";
    }

    private Question matchQuestion(int index) {
        for (Question question : questions.getQuestions()) if (question.isSameQuestion(index)) return question;
        return null;
    }
}
