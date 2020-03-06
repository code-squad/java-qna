package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogin(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(sessionUser, question, contents);
        answerRepository.save(answer);
        return "redirect:/questions/" + questionId;
    }

    @GetMapping("/{id}/form")
    public String viewUpdateForm(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("answer", getVerifiedAnswer(id, questionId, session));
            return "/qna/updatedAnswerForm";
        } catch (NullPointerException | IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    @PutMapping("/{id}/form")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long id, String contents, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, questionId, session);
            answer.update(contents);
            answerRepository.save(answer);
            return "redirect:/questions/" + questionId;
        } catch (NullPointerException | IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, questionId,session);
            answerRepository.delete(answer);
            return "redirect:/questions/" + questionId;
        } catch (NullPointerException | IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return e.getMessage();
        }
    }

    private void checkNotFound(Long id, Long questionId) {
        if (!answerRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("/error/notFound");
        }
        if (!questionRepository.findById(questionId).isPresent()) {
            throw new EntityNotFoundException("/error/notFound");
        }
    }

    private Answer getVerifiedAnswer(Long id, Long questionId, HttpSession session) throws IllegalAccessException {
        checkNotFound(id, questionId);
        if (!HttpSessionUtils.isLogin(session)) {
            throw new NullPointerException("/error/unauthorized");
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerRepository.getOne(id);
        if (!answer.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        return answer;
    }
}
