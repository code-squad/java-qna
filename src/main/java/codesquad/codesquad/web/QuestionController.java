package codesquad.codesquad.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String quetioning(Question question){
        question.setIndex(questions.size()+1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getBackToIndex(Model model){
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String getBackToIndex(@PathVariable int id, Model model){
        model.addAttribute("question", questions.get(id-1));
        return "qna/show";
    }
}

