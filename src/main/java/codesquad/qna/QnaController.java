package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    public static List<Qna> questions = new ArrayList<>();

    @GetMapping("/questions/form")
    public String form() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String create(Qna qna) {
        questions.add(qna);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String show(@PathVariable int index, Model model) {
        model.addAttribute("question", questions.get(index - 1));
        return "qna/show";
    }
    
}
