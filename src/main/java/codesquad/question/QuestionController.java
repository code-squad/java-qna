package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/questions")
@Controller
public class QuestionController {
    @PostMapping
    public String create(Question question) {
        question.setIndex(QuestionRepository.getQuestions().size() + 1);
        QuestionRepository.addQuestion(question);
        return "redirect:/questions/posts";
    }

    @GetMapping("/posts")
    public String list(Model model) {
        model.addAttribute("questions", QuestionRepository.getQuestions());
        return "index";
    }

    @GetMapping("/{index}")
    public String eachQuestion(Model model, @PathVariable int index) {
        model.addAttribute("question", QuestionRepository.findMatchQuestion(index));
        return "/qna/show";
    }
}