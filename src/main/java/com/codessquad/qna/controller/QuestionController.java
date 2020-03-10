package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        LOGGER.debug("[page] : {}", "질문작성 폼");

        Object sessionUser = session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if(sessionUser == null) {
            LOGGER.debug("[page] : {}", "비로그인 상태");
            return "redirect:/users/loginForm";
        }

        return "qna/form";
    }

    @PostMapping("")
    public String createQuestion(Question question, HttpSession session) {
        LOGGER.debug("[page] : {}", "질문 작성");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인 상태");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        if(!user.matchName(question.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        Question createdQuestion = Optional.ofNullable(question).orElseThrow(() -> new NullPointerException("NULL"));
        createdQuestion.setWriteTimeNow();

        LOGGER.debug("[page] : {}", "질문 DB에 저장");
        questionRepository.save(createdQuestion);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) throws NotFoundException {
        LOGGER.debug("[page] : {}", "질문 상세보기");
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다.")));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) throws NotFoundException {
        LOGGER.debug("[page] : {}", "질문 수정 폼");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
        if(!user.matchName(question.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, HttpSession session, Question updatedQuestion) throws NotFoundException {
        LOGGER.debug("[page] : {}", "질문 수정");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        if(!user.matchName(updatedQuestion.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) throws NotFoundException {
        LOGGER.debug("[page] : {}", "질문 삭제");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
        if(!user.matchName(question.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        questionRepository.deleteById(id);

        return "redirect:/";
    }

}
