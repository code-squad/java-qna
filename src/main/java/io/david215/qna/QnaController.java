package io.david215.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QnaController {
    Map<Integer, Question> questions = new HashMap<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questions.values());
        return "home";
    }

    @PostMapping("/questions")
    public String createNewQuestion(Question question) {
        questions.put(question.getId(), question);
        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String show(Model model, @PathVariable int id) {
        if (questions.containsKey(id)) {
            Question question = questions.get(id);
            model.addAttribute("question", question);
            return "qna/show";
        }
        return "qna/not-found";
    }
}
