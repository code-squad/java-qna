package com.codessquad.web.question;

import com.codessquad.domain.question.Question;
import com.codessquad.domain.question.QuestionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions/form")
    public String form() {
        return "/qna/form";
    }

    @PostMapping("/questions")
    public String create(Question newQuestion) {
        newQuestion.setDateTime(LocalDateTime.now());
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/main";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문 입니다.")));
            return "qna/show";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문 입니다.")));
            return "/qna/updateForm";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/questions/{id}")
    public String update(@PathVariable Long id, Question updateQuestion) {
        Question question = questionRepository.findById(id).get();
        updateQuestion.setDateTime(LocalDateTime.now());
        question.update(updateQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }
}
