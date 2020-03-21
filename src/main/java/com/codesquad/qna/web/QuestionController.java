package com.codesquad.qna.web;

import com.codesquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/questions/form")
    public String questionForm(HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException(); // 중복 코드 리팩토링
        }

        User writer = HttpSessionUtils.getUserFromSession(session);
        model.addAttribute("userName", writer.getUserName());

        return "/qna/form";
    }
    @PostMapping("/questions")
    public String writeQuestion(Question question, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        question.setWriter(sessionedUser);
        DatabaseUtils.replaceEscapesToTags(question);
        questionRepository.save(question);
        logger.info("{} 질문글의 등록에 성공 하였습니다.", question);

        return "redirect:/";
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("questions", questionRepository.findAll());

        return "index";
    }

    @GetMapping("/questions/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        model.addAttribute("question", selectedQuestion);

        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (!selectedQuestion.isSameWriter(sessionedUser)) {
            throw new UserNotPermittedException();
        }

        DatabaseUtils.replaceTagsToEscapes(selectedQuestion);
        model.addAttribute("question", selectedQuestion);

        return "qna/updateForm";
    }

    @PutMapping("/questions/{id}")
    public String updateQuestion(@PathVariable Long id, Question updatedQuestion, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (!selectedQuestion.isSameWriter(sessionedUser)) {
            throw new UserNotPermittedException();
        }

        selectedQuestion.update(updatedQuestion);
        DatabaseUtils.replaceEscapesToTags(selectedQuestion);
        questionRepository.save(selectedQuestion);
        logger.info("{} 질문글 수정에 성공 하였습니다.", selectedQuestion);

        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new UserNotPermittedException();
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question selectedQuestion = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if (!selectedQuestion.isSameWriter(sessionedUser)) {
            throw new UserNotPermittedException();
        }

        logger.info("{} 질문글 삭제에 성공 하였습니다.", selectedQuestion);
        questionRepository.delete(selectedQuestion);

        return "redirect:/";

    }
}
