package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/createForm")
    public String createForm(HttpSession session) {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("/create")
    public String create(Question question, Model model, HttpSession session) {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();
        Question createdQuestion = new Question(user, question);
        questionRepository.save(createdQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) throws IllegalAccessException {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();

        Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!user.getUserId().equals(question.getWriter())) {
            throw new IllegalAccessException("다른 사람의 글을 수정할 수 없습니다");
        }
        model.addAttribute("updateQuestion", question);
        return "qna/form";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, HttpSession session, Question updateQuestion) throws IllegalAccessException {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        Question question = questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        User user = (User) sessionedUser.get();
        if (!user.getUserId().equals(question.getWriter())) {
            throw new IllegalAccessException("다른 사람의 글을 수정할 수 없습니다");
        }
        question.update(updateQuestion);
        questionRepository.save(question);
        return "redirect:/questions/{id}";
    }
}
