package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(Exception::new);
            model.addAttribute("question", question);
            return "/qna/show";
        } catch (Exception e) {
            return "error/qna/notFoundQna";
        }
    }

    @GetMapping("questions/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(Exception::new);
            model.addAttribute("question", question);
            return "/qna/edit";
        } catch (Exception e) {
            return "error/qna/notFoundQna";
        }
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        questionRepository.save(question);
        return "redirect:/";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, Question updateQuestion) {
        Question question = questionRepository.findById(id).get();
        question.update(updateQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }
}
