package com.codessquad.qna;

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
    public String createQuestion(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping("/form")
    public String makeQuestion(String title, String contents, HttpSession session) {
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = new Question(loginUser, title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView questionShowDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/qna/show");
        modelAndView.addObject("question", questionRepository.getOne(id));
        System.out.println(questionRepository.getOne(id));
        return modelAndView;
    }

    @GetMapping("/{id}/modifyQuestion")
    public String modifyQuestion(@PathVariable Long id, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.getOne(id);
        if (question.authorizeUser(loginUser)) {
            model.addAttribute("question", question);
            return "/qna/modify_form";
        }
        return "/qna/not_qualified";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.getOne(id);
        question.updateQuestion(title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        Question question = questionRepository.getOne(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (question.authorizeUser(loginUser)) {
            question.deletQuestion();
            questionRepository.save(question);
            return "redirect:/";
        }
        return "/qna/not_qualified";
    }
}
