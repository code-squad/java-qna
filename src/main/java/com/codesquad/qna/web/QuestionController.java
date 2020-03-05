package com.codesquad.qna.web;

import com.codesquad.qna.model.*;
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

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String createForm(HttpSession session) {
        if (HttpSessionUtils.isNotLoggedIn(session))
            return "redirect:/user/login";

        return "qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNotLoggedIn(session))
            return "redirect:/user/login";

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionedUser, title, contents);
        questionRepository.save(newQuestion);
        log.info("create : {}, writer : {}", newQuestion, sessionedUser);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Question question = findQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("answers", answerRepository.findByQuestionIdAndDeletedFalse(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        if (HttpSessionUtils.isNotLoggedIn(session))
            return "redirect:/user/login";

        Question updateQuestion = getMatchedQuestion(id, session);
        model.addAttribute("question", updateQuestion);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
        if (HttpSessionUtils.isNotLoggedIn(session))
            return "redirect:/user/login";

        Question updateQuestion = getMatchedQuestion(id, session);
        updateQuestion.update(title, contents);
        questionRepository.save(updateQuestion);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (HttpSessionUtils.isNotLoggedIn(session))
            return "redirect:/user/login";

        Question deleteQuestion = getMatchedQuestion(id, session);
        if (!deleteQuestion.delete())
            throw new IllegalStateException(ErrorMessage.ILLEGAL_STATE.getMessage());
        // deleted 필드 변경 사항 저장
        questionRepository.save(deleteQuestion);
        answerRepository.findByQuestionIdAndDeletedFalse(id).stream()
                .forEach(answer -> {
                    answer.setDeleted(true);
                    answerRepository.save(answer);
        });
        return "redirect:/";
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ILLEGAL_ARGUMENT.getMessage()));
    }

    private Question getMatchedQuestion(Long questionId, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question matchedQuestion = findQuestion(questionId);
        log.info("matchedQuestion : {}, sessionedUser : {}", matchedQuestion, sessionedUser);
        if (!matchedQuestion.matchWriter(sessionedUser))
            throw new IllegalStateException(ErrorMessage.ILLEGAL_STATE.getMessage());

        return matchedQuestion;
    }
}
