package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/question/create")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/question/list";
    }

    @GetMapping("/question/list")
    public String questionList(Model model) {
        model.addAttribute("questions", questions);
        return "/index";
    }

    @GetMapping("/qna/show")
    public String qnaShow(){
        return "/qna/show";
    }
}
