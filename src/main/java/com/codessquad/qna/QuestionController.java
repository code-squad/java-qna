package com.codessquad.qna;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("question", questionRepository.findAll());
        return "index";
    }

    @PostMapping("/question/create")
    public String create(Question question) {
        question.setLocalDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        questionRepository.save(question);
        System.out.println("question : " + question);
        return "redirect:/";
    }

    @GetMapping("/question/{id}/show")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));
        return "/question/show";
    }
}
