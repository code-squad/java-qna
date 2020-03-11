package com.codesquad.qna.web;

import com.codesquad.qna.domain.AnswerRepository;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepostory;
import com.codesquad.qna.domain.User;
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
        try {
            Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어욧!!"));
            hasPermission(session, currentQuestion);
            model.addAttribute("currentQuestion", currentQuestion);
            return "/questions/updateForm";
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }
    }

    @PutMapping("/{id}")
    public String updateQuestion(Question updateQuestion, @PathVariable Long id, Model model, HttpSession session) {
        try {
            Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시물 없어영~!"));
            hasPermission(session, currentQuestion);
            currentQuestion.update(updateQuestion);
            questionRepostory.save(currentQuestion);
            return String.format("redirect:/questions/%d", id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }
    }

    @DeleteMapping("/{id}")
    public String removeQuestion(Question deleteQuestion, @PathVariable Long id, Model model, HttpSession session) {
        try {
            Question currentQuestion = questionRepostory.findById(id).orElseThrow(() -> new NotFoundException("게시글 없는데요??!!!"));
            hasPermission(session, currentQuestion);
            currentQuestion.delete();
            questionRepostory.save(currentQuestion);
            return "redirect:/questions";
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        }
    }

    @GetMapping("")
    public String viewQnaList(Model model) {
        model.addAttribute("questions", questionRepostory.findByDeletedFalse());
        return "/questions/list";
    }

    private boolean hasPermission(HttpSession session, Question currentQuestion) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if(!currentQuestion.matchUser(sessionUser)) {
            throw new IllegalStateException("자신의 게시글만 수정할 수 있습니다.");
        }

        return true;
    }

}
