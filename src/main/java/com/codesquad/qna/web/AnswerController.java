package com.codesquad.qna.web;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.AnswerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/questions/{{question.id}}/answers")
public class AnswerController {
    private AnswerRepository answerRepository;

    @GetMapping()
    public String createForm() {

    }

    @PostMapping()
    public String create() {

    }

    @GetMapping("/{id}/form")
    public String updateForm() {
        return "";
    }

    @PutMapping("/{id}/")
    public String update() {
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        answerRepository.delete(answer);
        return "redirect:/questions/{question.id}";
    }
}
