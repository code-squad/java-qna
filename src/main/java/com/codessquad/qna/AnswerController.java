package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/{questionId}/answers")
    public String answer(@PathVariable Long questionId,
                         @RequestParam String contents,
                         HttpSession httpSession,
                         Model model) {

        try {
            User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);
            hasPermission(httpSession);
            Question question = findQuestionById(questionRepository, questionId);
            Answer answer = new Answer(question, contents, sessionedUser);
            answerRepository.save(answer);
            return String.format("redirect:/questions/%d", questionId);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @GetMapping("/{questionId}/answers/{id}/{writer}/form")
    public String updateForm(@PathVariable Long questionId,
                             @PathVariable("id") Long answerId,
                             @PathVariable String writer,
                             HttpSession httpSession,
                             Model model) {

        try {
            hasPermission(httpSession, writer);
            model.addAttribute("question", findQuestionById(questionRepository, questionId));
            model.addAttribute("answer", findAnswerById(answerRepository, answerId));
            return "answer/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{questionId}/answers/{answer.id}/update")
    public String update(@PathVariable("answer.id") Long answerId,
                         String contents,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession);
            Answer answer = findAnswerById(answerRepository, answerId);
            answer.update(contents);
            answerRepository.save(answer);
            return "redirect:/questions/{questionId}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{questionId}/answers/{id}/{writer}/delete")
    public String delete(@PathVariable("id") Long answerId,
                         @PathVariable String writer,
                         HttpSession httpSession,
                         Model model) {
        try {
            hasPermission(httpSession, writer);
            Answer answer = findAnswerById(answerRepository, answerId);
            answer.delete();
            answerRepository.save(answer);
            return "redirect:/questions/{questionId}";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
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

    private Answer findAnswerById(AnswerRepository answerRepository, Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new IllegalStateException("There is no answer"));
    }

    private Question findQuestionById(QuestionRepository questionRepository, Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
