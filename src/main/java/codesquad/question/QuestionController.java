package codesquad.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private static List<Question> questions = new ArrayList<>();

    @GetMapping("/questions/form")
    public String create(Question question) {
        questions.add(question);
        return "redirect:/questions";
    }

    @PostMapping("/questions")
    public String list() {

        return "/";
    }

}
