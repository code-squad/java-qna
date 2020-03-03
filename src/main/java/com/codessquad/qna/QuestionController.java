package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "qna/list";
    }

    @GetMapping("/questions/form")
    public String goForm(HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        return "qna/form";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        LocalDateTime nowTime = LocalDateTime.now();
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;
        question.createNewQuestion(loginUser.getName(), nowTime);
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/questions/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);

        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String modifyQuestionForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        assert loginUser != null;
        if (!question.getWriter().equals(loginUser.getName())) {
            throw new IllegalStateException("자기 자신의 질문만 수정 가능합니다.");
        }
        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        assert loginUser != null;
        if (!question.getWriter().equals(loginUser.getName())) {
            throw new IllegalStateException("자기 자신의 질문만 수정 가능합니다.");
        }
        question.updateQuestion(title, contents);
        questionRepository.save(question);
        model.addAttribute("question", question);

        return "redirect:/questions/{id}";
    }
}
