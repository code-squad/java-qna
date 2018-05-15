package codesquad.web.controller;

import codesquad.web.domain.Question;
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

    @PostMapping("/qna/submit")
    public String question(Question question) {
        System.out.println(question.toString());
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showQuestions(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/qna/{id}")
    public String showUser(@PathVariable("id") String id, Model model) {
        System.out.println(questions.get(Integer.parseInt(id)).toString());
        model.addAttribute("question", questions.get(Integer.parseInt(id)));
        return "qna/show";
    }

}
