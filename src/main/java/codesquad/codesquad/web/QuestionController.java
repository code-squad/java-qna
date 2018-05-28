package codesquad.codesquad.web;

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

    @GetMapping("/questions/form")
    public String form(Question question) {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(Question question){
        question.setId(questions.size()+1);
        questions.add(question);
        System.out.println("post questions");
        return "redirect:/";
    }

    @GetMapping("/")
    public String getBackToIndex(Model model){
        model.addAttribute("questions", questions);
        System.out.println("get questions");
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getBackToIndex(@PathVariable int id, Model model){
        model.addAttribute("question", questions.get(id-1));
        return "show";
    }
}

