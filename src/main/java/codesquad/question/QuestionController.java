package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();


    @PostMapping("/question")
    public String question(Question question) {
        question.setId(questions.size());
        questions.add(question);
        return "redirect:/index";
    }

    @GetMapping("/show/{id}")
    public String qnaShow(Model model, @PathVariable int id) {
        model.addAttribute("question", questions.get(id));
        return "qna/show";
    }

    @GetMapping("qna/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }
}
