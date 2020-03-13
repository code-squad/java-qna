package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.InvalidInputException;
import com.codessquad.qna.exception.NotFoundException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String showQuestionForm(HttpSession session) {
        LOGGER.debug("[page] : {}", "질문작성 폼");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인 상태");
            return "redirect:/users/loginForm";
        }


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


        if(question == null) {
            throw new InvalidInputException("입력값이 유효하지 않습니다.");
        }
        question.setWriteTimeNow();

        LOGGER.debug("[page] : {}", "질문 DB에 저장");
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        LOGGER.debug("[page] : {}", "질문 상세보기");
        model.addAttribute("question", getQuestionById(id));
        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String showUpdateForm(@PathVariable Long id, HttpSession session, Model model)  {
        LOGGER.debug("[page] : {}", "질문 수정 폼");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = getQuestionById(id);
        if(!user.matchName(question.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, HttpSession session, Question updatedQuestion) {
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

        Question question = getQuestionById(id);
        question.update(updatedQuestion);
        questionRepository.save(question);

        return "redirect:/questions/"+id;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        LOGGER.debug("[page] : {}", "질문 삭제");

        if(!HttpSessionUtils.isLoginUser(session)) {
            LOGGER.debug("[page] : {}", "비로그인");
            return "redirect:/users/loginForm";
        }

        User user = HttpSessionUtils.getUserFromSession(session);
        Question question = getQuestionById(id);
        if(!user.matchName(question.getWriter())) {
            LOGGER.debug("[page] : {}", "글 작성자 아님");
            throw new IllegalStateException("글 작성자 아님");
        }

        questionRepository.deleteById(id);

        return "redirect:/";
    }

    private Question getQuestionById(Long id){
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("존재하지 않는 질문입니다."));
    }

}
