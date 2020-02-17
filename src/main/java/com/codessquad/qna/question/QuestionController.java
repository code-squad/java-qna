package com.codessquad.qna.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    List<Question> questions = new ArrayList<>();

    @GetMapping("/")
    public String goIndexPage(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/qna/form")
    public String goQnaForm() {
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(HttpServletRequest request) {
        int index = questions.size() + 1;
        String writer = request.getParameter("writer");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        Question newQuestion = new Question(index, writer, title, contents);
        questions.add(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable int index, Model model) {
        model.addAttribute("question", questions.get(index - 1));
        return "qna/show";
    }

}
