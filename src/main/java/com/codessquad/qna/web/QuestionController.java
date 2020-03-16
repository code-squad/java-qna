package com.codessquad.qna.web;

import com.codessquad.qna.domain.*;
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
            return "user/login";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "user/login";
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
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("question", question);

        return "qna/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateQuestion(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Result result = valid(session, question);

        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        question.updateQuestion(title, contents);
        questionRepository.save(question);

        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Result result = valid(session, question);

        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        question.deleteQuestion();
        questionRepository.save(question);

        return "redirect:/";
    }

    private boolean isSameWriterAndLoginUser(User loginUser, Question question) {
        return question.getWriter().getUserId().equals(loginUser.getUserId());
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;
        if (!isSameWriterAndLoginUser(loginUser, question)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능 합니다.");
        }
        return Result.ok();
    }
}
