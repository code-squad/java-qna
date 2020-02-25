package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


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
    public Object showQuestion(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "/qna/show";
        }
        return "redirect:/error/notFound";
    }

    @GetMapping("/questions/{id}/editForm")
    public Object showEditPage(@PathVariable Long id, Model model) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "/qna/edit";
        }
        return "redirect:/error/notFound";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        if(isCorrectForm(question)) {
            questionRepository.save(question);
            return "redirect:/";
        }
        return "redirect:/error/badRequest";
    }

    @PutMapping("/questions/{id}")
    public Object updateQuestion(@PathVariable Long id, Question updateQuestion) {
        Optional<Question> originalQuestion = questionRepository.findById(id);
        return originalQuestion.map(question -> update(question, updateQuestion)).orElseGet(ResponseEntity::notFound);
    }

    private Object update(Question originalQuestion, Question updateQuestion) {
        if (isCorrectForm(updateQuestion)){
            originalQuestion.update(updateQuestion);
            questionRepository.save(originalQuestion);
            return "redirect:/questions/{id}";
        }
        return "redirect:/error/badRequest";
    }

    private boolean isCorrectForm(Question question) {
        boolean titleIsExist = ObjectUtils.isNotEmpty(question.getTitle());
        boolean contentIsExist = ObjectUtils.isNotEmpty(question.getContents());
        boolean writerIsExist = ObjectUtils.isNotEmpty(question.getWriter());

        return titleIsExist && contentIsExist && writerIsExist;
    }
}
