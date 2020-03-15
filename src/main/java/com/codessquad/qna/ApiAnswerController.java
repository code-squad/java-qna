package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions")
public class ApiAnswerController {
    private final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/{questionId}/answers")
    public Answer create(@PathVariable Long questionId,
                         String contents,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession);
            User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);
            Question question = findQuestionById(questionId);
            Answer answer = new Answer(question, contents, sessionedUser);
            return answerRepository.save(answer);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    @GetMapping("/{questionId}/answers/{answerId}/{writer}/form")
    public String updateForm(@PathVariable Long questionId,
                             @PathVariable Long answerId,
                             @PathVariable String writer,
                             HttpSession httpSession,
                             Model model) {
        try {
            hasPermission(httpSession, writer);
            model.addAttribute("question", findQuestionById(questionId));
            model.addAttribute("answer", findAnswerById(answerId));
            return "answer/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{questionId}/answers/{answerId}/update")
    public String update(@PathVariable Long answerId,
                         String contents,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession);
            Answer answer = findAnswerById(answerId);
            answer.update(contents);
            answerRepository.save(answer);
            return "redirect:/questions/{questionId}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{questionId}/answers/{answerId}/{writer}/delete")
    public Result delete(@PathVariable Long answerId,
                         @PathVariable String writer,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession, writer);
            Answer answer = findAnswerById(answerId);
            answer.delete();
            answerRepository.save(answer);
            return Result.ok();
        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
            return Result.fail("실패했습니다.");
        }
    }

    private void hasPermission(HttpSession httpSession, String writer) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            throw new IllegalStateException("일치하는 아이디가 없습니다.");
        }

        if (sessionedUser.notMatchWriter(writer)) {
            throw new IllegalStateException("작성자만 삭제할 수 있습니다.");
        }
    }

    private void hasPermission(HttpSession httpSession) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
    }

    private Answer findAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new IllegalStateException("There is no answer"));
    }

    private Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
