package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.isLogin;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/{id}/form")
    public String viewUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("answer", getVerifiedAnswer(id, session));
            return "/qna/updatedAnswerForm";
        } catch (IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > {} ", e.toString());
            return e.getMessage();
        }
    }

    @PutMapping("/{id}/form")
    public String updateAnswer(@PathVariable Long questionId, @PathVariable Long id, String contents, HttpSession session) {
        try {
            Answer answer = getVerifiedAnswer(id, session);
            answer.update(contents);
            answerRepository.save(answer);
            return "redirect:/questions/" + questionId;
        } catch (IllegalAccessException | EntityNotFoundException e) {
            log.info("Error Code > {} ", e.toString());
            return e.getMessage();
        }
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
