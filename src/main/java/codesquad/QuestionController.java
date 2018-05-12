package codesquad;

import codesquad.model.Question;
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

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/submit")
    public String submit(Question question) {
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/question/{index}")
    public String showQuestion(@PathVariable String index, Model model) {
        for (Question question : questions) {
            if (question.getIndex() == Integer.parseInt(index)) {
                model.addAttribute("question", question);
                break;
            }
        }
        return "qna/show";
    }
}
