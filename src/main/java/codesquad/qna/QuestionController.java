package codesquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public String create(Question question) {
        questionRepository.save(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/{index}")
    public String show(Model model, @PathVariable Long index) {
        model.addAttribute("question", questionRepository.findById(index).orElse(null));
        return "/qna/show";
    }
}
