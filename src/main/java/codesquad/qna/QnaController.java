package codesquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnaController {
    private List<Question> questions = new ArrayList<>();
    @PostMapping
    public String create(Question question){
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable int index, Model model){
        model.addAttribute("question", questions.get(index-1));
        return "qna/show";
    }
}
