package codesquad;

import codesquad.model.Question;
import codesquad.model.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questionRepository.findAllByOrderByIdDesc());
        return "index";
    }

    @PostMapping("/submit")
    public String submit(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/question/{index}")
    public String showQuestion(@PathVariable Long index, Model model) {
        Question question = questionRepository.findOne(index);
        model.addAttribute("question", question);
        return "questions/show";
    }
}
