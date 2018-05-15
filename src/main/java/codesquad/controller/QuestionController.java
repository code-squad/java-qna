package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.Questions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class QuestionController {
    private Questions questions = new Questions();

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questions.getQuestions());
        return "index";
    }

    @PostMapping("/questions")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        Optional<Question> question = questions.findById(id);
        if (!question.isPresent()) {
            System.out.println("존재하지않는 게시글임"); // inner log
            return "/error/show";
        }
        model.addAttribute("question", question.get());
        return "/question/show";
    }
}
