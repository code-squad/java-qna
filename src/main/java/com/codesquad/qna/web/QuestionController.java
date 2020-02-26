package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String createForm(HttpSession session) {
        if (!HttpSessionUtils.isLogined(session))
            return "redirect:/user/login";

        return "qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session))
            return "redirect:/user/login";

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUser, title, contents, LocalDateTime.now());
        questionRepository.save(newQuestion);
        log.info("create : {}", newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable Long questionId, Model model) {
        Question focusQuestion = findQuestion(questionId);
        model.addAttribute("question", focusQuestion);
        return "qna/show";
    }

    @GetMapping("/{questionId}/form")
    public String updateForm(@PathVariable Long questionId, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLogined(session))
            return "redirect:/user/login";

        Question updateQuestion = getMatchedQuestion(questionId, session);
        model.addAttribute("question", updateQuestion);
        return "qna/updateForm";
    }

    @PutMapping("/{questionId}/update")
    public String update(@PathVariable Long questionId, String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session))
            return "redirect:/user/login";

        Question updateQuestion = getMatchedQuestion(questionId, session);
        updateQuestion.update(title, contents);
        questionRepository.save(updateQuestion);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/delete")
    public String delete(@PathVariable Long questionId, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session))
            return "redirect:/user/login";

        Question deleteQuestion = getMatchedQuestion(questionId, session);
        questionRepository.delete(deleteQuestion);
        return "redirect:/";
    }

    private Question findQuestion(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
    }

    private Question getMatchedQuestion(Long questionId, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question matchedQuestion = findQuestion(questionId);
        if (!matchedQuestion.matchWriter(sessionedUser))
            throw new IllegalStateException("You can only update your own");

        return matchedQuestion;
    }
}
