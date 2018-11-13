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
    QuestionRepository questionRepository = QuestionRepository.INSTANCE;

    @GetMapping("/form")
    public String form() {
        return "qna/form";
    }

    @PostMapping()
    public String create(Question question) {
        questionRepository.addQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable int index, Model model) {
        model.addAttribute("question", questionRepository.findQuestion(index - 1));
        return "qna/show";
    }
    
}
