package com.codessquad.qna.question;

import com.codessquad.qna.answer.AnswerRepository;
import com.codessquad.qna.utils.HttpSessionUtils;
import com.codessquad.qna.user.User;
import com.codessquad.qna.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String qnaForm(HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestionContents(@PathVariable Long id, Model model) {
        model.addAttribute("question",
                questionRepository.findById(id).orElseThrow(IllegalStateException::new));

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        Result result = valid(session, question);
        if (result.isNotValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents,
                                 Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        Result result = valid(session, question);
        if (result.isNotValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("question", question);
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(IllegalStateException::new);
        Result result = valid(session, question);
        if (result.isNotValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }

    private Result valid(HttpSession session, Question question) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (question.isNotSameWriter(loginUser)) {
            return Result.fail("자신의 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

}
