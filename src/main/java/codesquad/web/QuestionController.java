package codesquad.web;

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

    @PostMapping("/questions")
    public String saveQuestion(Question question){
        question.setId(questions.size()+1);
        questions.add(question);
        System.out.println(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String goHome(Model model){
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String goHome(@PathVariable int id, Model model){
        model.addAttribute("question", questions.get(id-1));
        return "qna/show";
    }
}
