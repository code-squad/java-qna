package com.codessquad.qna.web;

import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
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

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("")
    public String showQuestionList(Model model) {
        model.addAttribute("questions", questionRepository.findAllByActivedeleted());
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
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(NoSuchElementException::new));
        model.addAttribute("answers", answerRepository.findAllActiveAnswers(id));

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String modifyQuestionForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
            hasPermission(session, question);
            model.addAttribute("question", question);

            return "qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
            hasPermission(session, question);
            question.updateQuestion(title, contents);
            questionRepository.save(question);

            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model, HttpSession session) {

        try {
            Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
            hasPermission(session, question);
            question.deleteQuestion();
            questionRepository.save(question);

            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private boolean isSameWriterAndLoginUser(User loginUser, Question question) {
        return question.getWriter().getUserId().equals(loginUser.getUserId());
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!isSameWriterAndLoginUser(loginUser, question)) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능 합니다.");
        }
        return false;
    }
}
