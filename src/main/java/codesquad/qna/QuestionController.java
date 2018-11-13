package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    public static List<Question> questions = new ArrayList<>();

    @PostMapping("/qna/create")
    public String create(Question question) {
        System.out.println("excute create!");
        questions.add(question);
        question.setId(questions.size());
        System.out.println("user : " + question);
        System.out.println("id : " + question.getId());
        return "redirect:/";
    }

    @GetMapping("/qna/form")
    public String qnaForm() {
        return "qna/form";
    }

    @GetMapping("/questions/{index}")
    public String showpage(@PathVariable int index, Model model) {
        model.addAttribute("list", questions.stream()
                .filter(q -> q.getId() == index)
                .findFirst()
                .orElse(null));
        return "qna/show";
    }
}
