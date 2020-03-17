package com.codesquad.qna.web;

import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.QuestionRepository;
import com.codesquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String create(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/form")
    public String make(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);
        return HomeController.HOME_DIRECTORY;
    }

    @GetMapping("/{id}")
    public ModelAndView showDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(id));
        System.out.println(questionRepository.getOne(id));
        return modelAndView;
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        Question question = questionRepository.getOne(id);
        if (question.authorizeUser(loginUser)) {
            model.addAttribute("question", question);
            return "/qna/update_form";
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.getOne(id);
        question.updateQuestion(title, contents);
        questionRepository.save(question);
        return HomeController.HOME_DIRECTORY;
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.getOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session).orElse(null);
        if (question.authorizeUser(loginUser)) {
            if (question.answerWriterCheck()) {
                return HomeController.NOT_AUTHORIZE_DIRECTORY;
            }
            question.deletQuestion();
            questionRepository.save(question);
            return HomeController.HOME_DIRECTORY;
        }
        return HomeController.NOT_AUTHORIZE_DIRECTORY;
    }
}
