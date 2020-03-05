package com.codesquad.qna.web;

import com.codesquad.qna.global.error.exception.DataNotFoundException;
import com.codesquad.qna.global.error.exception.ErrorCode;
import com.codesquad.qna.global.error.exception.RequestNotAllowedException;
import com.codesquad.qna.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/form")
    public String createForm() {
        return "qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, @RequestAttribute User sessionedUser) {
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
    public String updateForm(@PathVariable Long id, @RequestAttribute User sessionedUser, Model model) {
        Question updateQuestion = getMatchedQuestion(id, sessionedUser);
        model.addAttribute("question", updateQuestion);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, @RequestAttribute User sessionedUser) {
        Question updateQuestion = getMatchedQuestion(id, sessionedUser);
        updateQuestion.update(title, contents);
        questionRepository.save(updateQuestion);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestAttribute User sessionedUser) {
        Question deleteQuestion = getMatchedQuestion(id, sessionedUser);
        if (!deleteQuestion.delete())
            throw new RequestNotAllowedException(ErrorCode.DELETE_FAILED);
        // deleted 필드 변경 사항 저장
        questionRepository.save(deleteQuestion);
        answerRepository.findByQuestionIdAndDeletedFalse(id).forEach(answer -> {
            answer.setDeleted(true);
            answerRepository.save(answer);
        });
        return "redirect:/";
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException(ErrorCode.DATA_NOT_FOUND));
    }

    private Question getMatchedQuestion(Long questionId, User sessionedUser) {
        Question matchedQuestion = findQuestion(questionId);
        log.info("matchedQuestion : {}, sessionedUser : {}", matchedQuestion, sessionedUser);
        if (!matchedQuestion.matchWriter(sessionedUser))
            throw new RequestNotAllowedException(ErrorCode.FORBIDDEN);

        return matchedQuestion;
    }
}
