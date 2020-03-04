package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String createForm() {
        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question, Model model) {
        question.setCreatedDateTime(LocalDateTime.now());
        questionRepository.save(question);
        return "redirect:/questions/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(null);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
