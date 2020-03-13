package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/form")
    public String goForm(HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String modifyQuestionForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;

        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!isSameWriterAndLoginUser(loginUser, question)) {
            throw new IllegalStateException("자기 자신의 질문만 수정 가능합니다.");
        }

        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;

        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!isSameWriterAndLoginUser(loginUser, question)) {
            throw new IllegalStateException("자기 자신의 질문만 수정 가능합니다.");
        }

        question.updateQuestion(title, contents);
        questionRepository.save(question);
        model.addAttribute("question", question);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;

        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (!isSameWriterAndLoginUser(loginUser, question)) {
            throw new IllegalStateException("자기 자신의 질문만 삭제 가능합니다.");
        }

        questionRepository.delete(question);

        return "redirect:/";
    }

    private boolean isSameWriterAndLoginUser(User loginUser, Question question) {
        return question.getWriter().getUserId().equals(loginUser.getUserId());
    }
}
