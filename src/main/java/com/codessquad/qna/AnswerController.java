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

    @PostMapping("/{question.id}/answers")
    public String answer(@PathVariable("question.id") Long questionId,
                         @RequestParam String contents,
                         HttpSession httpSession) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }

        Question question = findQuestion(questionRepository, questionId);
        Answer answer = new Answer(question, contents, sessionedUser.getName());
        answerRepository.save(answer);
        return "redirect:/questions/{question.id}";
    }

    @GetMapping("/{question.id}/answers/{id}/{writer}/form")
    public String updateForm(@PathVariable("question.id") Long questionId,
                             @PathVariable("id") Long answerId,
                             @PathVariable String writer,
                             HttpSession httpSession,
                             Model model) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }

        if (sessionedUser.notMatchWriter(writer)) {
            return "redirect:/users/loginForm";
        }

        model.addAttribute("question", findQuestion(questionRepository, questionId));
        model.addAttribute("answers", answerRepository.findByQuestionId(questionId));
        model.addAttribute("answer", findAnswer(answerRepository, answerId));
        return "answer/updateForm";
    }

    @PutMapping("/{question.id}/answers/{answer.id}/update")
    public String update(@PathVariable("answer.id") Long answerId,
                         String contents,
                         HttpSession httpSession) {
        User user = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }

        Answer answer = findAnswer(answerRepository, answerId);
        answer.update(contents);
        answerRepository.save(answer);
        return "redirect:/questions/{question.id}";
    }

    @DeleteMapping("/{question.id}/answers/{id}/{writer}/delete")
    public String delete(@PathVariable("id") Long answerId,
                         @PathVariable String writer,
                         HttpSession httpSession) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }

        if (sessionedUser.notMatchWriter(writer)) {
            return "redirect:/users/loginForm";
        }

        answerRepository.delete(findAnswer(answerRepository, answerId));
        return "redirect:/questions/{question.id}";
    }

    private Answer findAnswer(AnswerRepository answerRepository, Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new IllegalStateException("There is no answer"));
    }

    private Question findQuestion(QuestionRepository questionRepository, Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("There is no question."));
    }
}
