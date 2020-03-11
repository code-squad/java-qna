package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
     private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepostory questionRepostory;

    @GetMapping("/form")
    public String moveQnaForm(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        return "/questions/form";
    }

    @PostMapping("")
    public String saveQuestions(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(sessionUser, title, contents);
        questionRepostory.save(question);
        return "redirect:/questions";
    }

    @GetMapping("/{id}")
    public String viewQuestion(@PathVariable long id, Model model) {
        try {
            model.addAttribute("currentQuestion", questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("자료가 없어용")));
            model.addAttribute("answers", answerRepository.findAllByQuestionId(id));
            return "/questions/show";
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/modify")
    public String moveUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        Question currentQuestion;
        try {
            currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어욧!!"));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        ValidationResult validationResult = validate(session, currentQuestion);
        if (!validationResult.isValid()) {
        model.addAttribute("errorMessage", validationResult.getErrorMessage());
        return "/error";
        }

        model.addAttribute("currentQuestion", currentQuestion);
        return "/questions/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(Question updateQuestion, @PathVariable Long id, Model model, HttpSession session) {
        Question currentQuestion;
        try {
            currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어영~!"));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        ValidationResult validationResult = validate(session, currentQuestion);
        if (!validationResult.isValid()) {
            model.addAttribute("errorMessage", validationResult.getErrorMessage());
            return "/error";
        }

        currentQuestion.update(updateQuestion);
        questionRepostory.save(currentQuestion);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String removeQuestion(Question deleteQuestion, @PathVariable Long id, Model model, HttpSession session) {
        Question currentQuestion;
        try {
            currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어영~!"));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        ValidationResult validationResult = validate(session, currentQuestion);
        if (!validationResult.isValid()) {
            model.addAttribute("errorMessage", validationResult.getErrorMessage());
            return "/error";
        }

        try {
            currentQuestion.delete();
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }
        questionRepostory.save(currentQuestion);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepostory.findByDeletedFalse());
        return "/questions/list";
    }

    private ValidationResult validate(HttpSession session, Question currentQuestion) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return ValidationResult.fail("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!currentQuestion.matchUser(sessionUser)) {
            return ValidationResult.fail("자신의 게시글만 수정 및 삭제할 수 있습니다.");
        }

        return ValidationResult.ok();
    }
}
