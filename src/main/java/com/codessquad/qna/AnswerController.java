package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.isLogin;
import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLogin(session)) {
            return null;
        }
        User sessionUser = getUserFromSession(session);
        Question question = findQuestion(questionId);
        Answer answer = new Answer(sessionUser, question, contents);
        return answerRepository.save(answer);
    }

    @GetMapping("/{id}/form")
    public ModelAndView viewUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("answer", getVerifiedAnswer(id, session));
            ModelAndView mav = new ModelAndView("/qna/updatedAnswerForm");
            return mav;//"/qna/updatedAnswerForm";
        } catch (IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return null;
            //return e.getMessage();
        }
    }

    @PutMapping("/{id}/form")
    public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long id, String contents, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, session);
            answer.update(contents);
            return answerRepository.save(answer);
            //return "redirect:/questions/" + questionId;
        } catch (IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return null;
            //return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, session);
            answer.delete();
            answerRepository.save(answer);
            return answer.isDeleted();
        } catch (IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > " + e.toString());
            return false;
        }
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("/error/notFound"));
    }

    private Answer findAnswer(Long id) {
        return answerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("/error/notFound"));
    }

    private Answer getVerifiedAnswer(Long id, HttpSession session) throws IllegalAccessException {
        if (!isLogin(session)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        User sessionUser = getUserFromSession(session);
        Answer answer = findAnswer(id);
        if (!answer.isWriterEquals(sessionUser)) {
            throw new IllegalAccessException("/error/forbidden");
        }
        return answer;
    }
}
